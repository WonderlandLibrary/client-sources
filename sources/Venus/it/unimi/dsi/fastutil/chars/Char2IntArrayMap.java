/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2IntMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
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
public class Char2IntArrayMap
extends AbstractChar2IntMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient int[] value;
    private int size;

    public Char2IntArrayMap(char[] cArray, int[] nArray) {
        this.key = cArray;
        this.value = nArray;
        this.size = cArray.length;
        if (cArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + nArray.length + ")");
        }
    }

    public Char2IntArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Char2IntArrayMap(int n) {
        this.key = new char[n];
        this.value = new int[n];
    }

    public Char2IntArrayMap(Char2IntMap char2IntMap) {
        this(char2IntMap.size());
        this.putAll(char2IntMap);
    }

    public Char2IntArrayMap(Map<? extends Character, ? extends Integer> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2IntArrayMap(char[] cArray, int[] nArray, int n) {
        this.key = cArray;
        this.value = nArray;
        this.size = n;
        if (cArray.length != nArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + nArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2IntMap.FastEntrySet char2IntEntrySet() {
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
    public int get(char c) {
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
    public int put(char c, int n) {
        int n2 = this.findKey(c);
        if (n2 != -1) {
            int n3 = this.value[n2];
            this.value[n2] = n;
            return n3;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            int n4 = this.size;
            while (n4-- != 0) {
                cArray[n4] = this.key[n4];
                nArray[n4] = this.value[n4];
            }
            this.key = cArray;
            this.value = nArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public int remove(char c) {
        int n = this.findKey(c);
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
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2IntArrayMap this$0;
            {
                this.this$0 = char2IntArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2IntArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2IntArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2IntArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2IntArrayMap.access$100(this.this$0), n + 1, Char2IntArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2IntArrayMap.access$200(this.this$0), n + 1, Char2IntArrayMap.access$200(this.this$0), n, n2);
                Char2IntArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2IntArrayMap.access$100(this.this$1.this$0), this.pos, Char2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2IntArrayMap.access$200(this.this$1.this$0), this.pos, Char2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2IntArrayMap.access$000(this.this$0);
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
            final Char2IntArrayMap this$0;
            {
                this.this$0 = char2IntArrayMap;
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
                        return this.pos < Char2IntArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2IntArrayMap.access$100(this.this$1.this$0), this.pos, Char2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2IntArrayMap.access$200(this.this$1.this$0), this.pos, Char2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2IntArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2IntArrayMap.access$000(this.this$0);
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

    public Char2IntArrayMap clone() {
        Char2IntArrayMap char2IntArrayMap;
        try {
            char2IntArrayMap = (Char2IntArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2IntArrayMap.key = (char[])this.key.clone();
        char2IntArrayMap.value = (int[])this.value.clone();
        return char2IntArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readInt();
        }
    }

    public ObjectSet char2IntEntrySet() {
        return this.char2IntEntrySet();
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

    static int access$000(Char2IntArrayMap char2IntArrayMap) {
        return char2IntArrayMap.size;
    }

    static char[] access$100(Char2IntArrayMap char2IntArrayMap) {
        return char2IntArrayMap.key;
    }

    static int[] access$200(Char2IntArrayMap char2IntArrayMap) {
        return char2IntArrayMap.value;
    }

    static int access$010(Char2IntArrayMap char2IntArrayMap) {
        return char2IntArrayMap.size--;
    }

    static int access$300(Char2IntArrayMap char2IntArrayMap, char c) {
        return char2IntArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2IntMap.Entry>
    implements Char2IntMap.FastEntrySet {
        final Char2IntArrayMap this$0;

        private EntrySet(Char2IntArrayMap char2IntArrayMap) {
            this.this$0 = char2IntArrayMap;
        }

        @Override
        public ObjectIterator<Char2IntMap.Entry> iterator() {
            return new ObjectIterator<Char2IntMap.Entry>(this){
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
                    return this.next < Char2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2IntMap.BasicEntry(Char2IntArrayMap.access$100(this.this$1.this$0)[this.curr], Char2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Char2IntMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2IntMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2IntMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2IntArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2IntArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2IntArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Integer)entry.getValue()).intValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            int n = (Integer)entry.getValue();
            int n2 = Char2IntArrayMap.access$300(this.this$0, c);
            if (n2 == -1 || n != Char2IntArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Char2IntArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Char2IntArrayMap.access$100(this.this$0), n2 + 1, Char2IntArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Char2IntArrayMap.access$200(this.this$0), n2 + 1, Char2IntArrayMap.access$200(this.this$0), n2, n3);
            Char2IntArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2IntArrayMap char2IntArrayMap, 1 var2_2) {
            this(char2IntArrayMap);
        }
    }
}

