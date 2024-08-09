/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2CharMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
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
public class Byte2CharArrayMap
extends AbstractByte2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] key;
    private transient char[] value;
    private int size;

    public Byte2CharArrayMap(byte[] byArray, char[] cArray) {
        this.key = byArray;
        this.value = cArray;
        this.size = byArray.length;
        if (byArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + cArray.length + ")");
        }
    }

    public Byte2CharArrayMap() {
        this.key = ByteArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Byte2CharArrayMap(int n) {
        this.key = new byte[n];
        this.value = new char[n];
    }

    public Byte2CharArrayMap(Byte2CharMap byte2CharMap) {
        this(byte2CharMap.size());
        this.putAll(byte2CharMap);
    }

    public Byte2CharArrayMap(Map<? extends Byte, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Byte2CharArrayMap(byte[] byArray, char[] cArray, int n) {
        this.key = byArray;
        this.value = cArray;
        this.size = n;
        if (byArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + byArray.length + ", " + cArray.length + ")");
        }
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + byArray.length + ")");
        }
    }

    public Byte2CharMap.FastEntrySet byte2CharEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
            return n;
        }
        return 1;
    }

    @Override
    public char get(byte by) {
        byte[] byArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (byArray[n] != by) continue;
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
    public boolean containsKey(byte by) {
        return this.findKey(by) != -1;
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
    public char put(byte by, char c) {
        int n = this.findKey(by);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = byArray;
            this.value = cArray;
        }
        this.key[this.size] = by;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(byte by) {
        int n = this.findKey(by);
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
    public ByteSet keySet() {
        return new AbstractByteSet(this){
            final Byte2CharArrayMap this$0;
            {
                this.this$0 = byte2CharArrayMap;
            }

            @Override
            public boolean contains(byte by) {
                return Byte2CharArrayMap.access$300(this.this$0, by) != -1;
            }

            @Override
            public boolean remove(byte by) {
                int n = Byte2CharArrayMap.access$300(this.this$0, by);
                if (n == -1) {
                    return true;
                }
                int n2 = Byte2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Byte2CharArrayMap.access$100(this.this$0), n + 1, Byte2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Byte2CharArrayMap.access$200(this.this$0), n + 1, Byte2CharArrayMap.access$200(this.this$0), n, n2);
                Byte2CharArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ByteIterator iterator() {
                return new ByteIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Byte2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public byte nextByte() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2CharArrayMap.access$100(this.this$1.this$0), this.pos, Byte2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2CharArrayMap.access$200(this.this$1.this$0), this.pos, Byte2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2CharArrayMap.access$000(this.this$0);
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
            final Byte2CharArrayMap this$0;
            {
                this.this$0 = byte2CharArrayMap;
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
                        return this.pos < Byte2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Byte2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Byte2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Byte2CharArrayMap.access$100(this.this$1.this$0), this.pos, Byte2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Byte2CharArrayMap.access$200(this.this$1.this$0), this.pos, Byte2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Byte2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Byte2CharArrayMap.access$000(this.this$0);
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

    public Byte2CharArrayMap clone() {
        Byte2CharArrayMap byte2CharArrayMap;
        try {
            byte2CharArrayMap = (Byte2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byte2CharArrayMap.key = (byte[])this.key.clone();
        byte2CharArrayMap.value = (char[])this.value.clone();
        return byte2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new byte[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readByte();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet byte2CharEntrySet() {
        return this.byte2CharEntrySet();
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

    static int access$000(Byte2CharArrayMap byte2CharArrayMap) {
        return byte2CharArrayMap.size;
    }

    static byte[] access$100(Byte2CharArrayMap byte2CharArrayMap) {
        return byte2CharArrayMap.key;
    }

    static char[] access$200(Byte2CharArrayMap byte2CharArrayMap) {
        return byte2CharArrayMap.value;
    }

    static int access$010(Byte2CharArrayMap byte2CharArrayMap) {
        return byte2CharArrayMap.size--;
    }

    static int access$300(Byte2CharArrayMap byte2CharArrayMap, byte by) {
        return byte2CharArrayMap.findKey(by);
    }

    private final class EntrySet
    extends AbstractObjectSet<Byte2CharMap.Entry>
    implements Byte2CharMap.FastEntrySet {
        final Byte2CharArrayMap this$0;

        private EntrySet(Byte2CharArrayMap byte2CharArrayMap) {
            this.this$0 = byte2CharArrayMap;
        }

        @Override
        public ObjectIterator<Byte2CharMap.Entry> iterator() {
            return new ObjectIterator<Byte2CharMap.Entry>(this){
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
                    return this.next < Byte2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractByte2CharMap.BasicEntry(Byte2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Byte2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Byte2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Byte2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractByte2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractByte2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Byte2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Byte2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Byte2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Byte2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Byte2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Byte2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Byte2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Byte2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Byte2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Byte2CharArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            return this.this$0.containsKey(by) && this.this$0.get(by) == ((Character)entry.getValue()).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Byte)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            byte by = (Byte)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n = Byte2CharArrayMap.access$300(this.this$0, by);
            if (n == -1 || c != Byte2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Byte2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Byte2CharArrayMap.access$100(this.this$0), n + 1, Byte2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Byte2CharArrayMap.access$200(this.this$0), n + 1, Byte2CharArrayMap.access$200(this.this$0), n, n2);
            Byte2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Byte2CharArrayMap byte2CharArrayMap, 1 var2_2) {
            this(byte2CharArrayMap);
        }
    }
}

