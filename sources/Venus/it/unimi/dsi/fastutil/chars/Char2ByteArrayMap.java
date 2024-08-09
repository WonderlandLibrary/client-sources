/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
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
public class Char2ByteArrayMap
extends AbstractChar2ByteMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient byte[] value;
    private int size;

    public Char2ByteArrayMap(char[] cArray, byte[] byArray) {
        this.key = cArray;
        this.value = byArray;
        this.size = cArray.length;
        if (cArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + byArray.length + ")");
        }
    }

    public Char2ByteArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = ByteArrays.EMPTY_ARRAY;
    }

    public Char2ByteArrayMap(int n) {
        this.key = new char[n];
        this.value = new byte[n];
    }

    public Char2ByteArrayMap(Char2ByteMap char2ByteMap) {
        this(char2ByteMap.size());
        this.putAll(char2ByteMap);
    }

    public Char2ByteArrayMap(Map<? extends Character, ? extends Byte> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2ByteArrayMap(char[] cArray, byte[] byArray, int n) {
        this.key = cArray;
        this.value = byArray;
        this.size = n;
        if (cArray.length != byArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + byArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2ByteMap.FastEntrySet char2ByteEntrySet() {
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
    public byte get(char c) {
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
    public boolean containsValue(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != by) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public byte put(char c, byte by) {
        int n = this.findKey(c);
        if (n != -1) {
            byte by2 = this.value[n];
            this.value[n] = by;
            return by2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                byArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = byArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = by;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public byte remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return this.defRetValue;
        }
        byte by = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return by;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2ByteArrayMap this$0;
            {
                this.this$0 = char2ByteArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2ByteArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2ByteArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2ByteArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2ByteArrayMap.access$100(this.this$0), n + 1, Char2ByteArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2ByteArrayMap.access$200(this.this$0), n + 1, Char2ByteArrayMap.access$200(this.this$0), n, n2);
                Char2ByteArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ByteArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Char2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Char2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ByteArrayMap.access$000(this.this$0);
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
    public ByteCollection values() {
        return new AbstractByteCollection(this){
            final Char2ByteArrayMap this$0;
            {
                this.this$0 = char2ByteArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return this.this$0.containsValue(by);
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2ByteArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ByteArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ByteArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ByteArrayMap.access$100(this.this$1.this$0), this.pos, Char2ByteArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ByteArrayMap.access$200(this.this$1.this$0), this.pos, Char2ByteArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ByteArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ByteArrayMap.access$000(this.this$0);
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

    public Char2ByteArrayMap clone() {
        Char2ByteArrayMap char2ByteArrayMap;
        try {
            char2ByteArrayMap = (Char2ByteArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2ByteArrayMap.key = (char[])this.key.clone();
        char2ByteArrayMap.value = (byte[])this.value.clone();
        return char2ByteArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeByte(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readByte();
        }
    }

    public ObjectSet char2ByteEntrySet() {
        return this.char2ByteEntrySet();
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

    static int access$000(Char2ByteArrayMap char2ByteArrayMap) {
        return char2ByteArrayMap.size;
    }

    static char[] access$100(Char2ByteArrayMap char2ByteArrayMap) {
        return char2ByteArrayMap.key;
    }

    static byte[] access$200(Char2ByteArrayMap char2ByteArrayMap) {
        return char2ByteArrayMap.value;
    }

    static int access$010(Char2ByteArrayMap char2ByteArrayMap) {
        return char2ByteArrayMap.size--;
    }

    static int access$300(Char2ByteArrayMap char2ByteArrayMap, char c) {
        return char2ByteArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2ByteMap.Entry>
    implements Char2ByteMap.FastEntrySet {
        final Char2ByteArrayMap this$0;

        private EntrySet(Char2ByteArrayMap char2ByteArrayMap) {
            this.this$0 = char2ByteArrayMap;
        }

        @Override
        public ObjectIterator<Char2ByteMap.Entry> iterator() {
            return new ObjectIterator<Char2ByteMap.Entry>(this){
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
                    return this.next < Char2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.access$100(this.this$1.this$0)[this.curr], Char2ByteArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2ByteMap.Entry> fastIterator() {
            return new ObjectIterator<Char2ByteMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2ByteMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2ByteMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2ByteArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ByteMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2ByteArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2ByteArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ByteArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ByteArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ByteArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ByteArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ByteArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2ByteArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Byte)entry.getValue()).byteValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Byte)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            byte by = (Byte)entry.getValue();
            int n = Char2ByteArrayMap.access$300(this.this$0, c);
            if (n == -1 || by != Char2ByteArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2ByteArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2ByteArrayMap.access$100(this.this$0), n + 1, Char2ByteArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2ByteArrayMap.access$200(this.this$0), n + 1, Char2ByteArrayMap.access$200(this.this$0), n, n2);
            Char2ByteArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2ByteArrayMap char2ByteArrayMap, 1 var2_2) {
            this(char2ByteArrayMap);
        }
    }
}

