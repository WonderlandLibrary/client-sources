/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2CharMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
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
public class Int2CharArrayMap
extends AbstractInt2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient char[] value;
    private int size;

    public Int2CharArrayMap(int[] nArray, char[] cArray) {
        this.key = nArray;
        this.value = cArray;
        this.size = nArray.length;
        if (nArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + cArray.length + ")");
        }
    }

    public Int2CharArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Int2CharArrayMap(int n) {
        this.key = new int[n];
        this.value = new char[n];
    }

    public Int2CharArrayMap(Int2CharMap int2CharMap) {
        this(int2CharMap.size());
        this.putAll(int2CharMap);
    }

    public Int2CharArrayMap(Map<? extends Integer, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Int2CharArrayMap(int[] nArray, char[] cArray, int n) {
        this.key = nArray;
        this.value = cArray;
        this.size = n;
        if (nArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + nArray.length + ", " + cArray.length + ")");
        }
        if (n > nArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + nArray.length + ")");
        }
    }

    public Int2CharMap.FastEntrySet int2CharEntrySet() {
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
    public char get(int n) {
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
    public char put(int n, char c) {
        int n2 = this.findKey(n);
        if (n2 != -1) {
            char c2 = this.value[n2];
            this.value[n2] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            int[] nArray = new int[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n3 = this.size;
            while (n3-- != 0) {
                nArray[n3] = this.key[n3];
                cArray[n3] = this.value[n3];
            }
            this.key = nArray;
            this.value = cArray;
        }
        this.key[this.size] = n;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(int n) {
        int n2 = this.findKey(n);
        if (n2 == -1) {
            return this.defRetValue;
        }
        char c = this.value[n2];
        int n3 = this.size - n2 - 1;
        System.arraycopy(this.key, n2 + 1, this.key, n2, n3);
        System.arraycopy(this.value, n2 + 1, this.value, n2, n3);
        --this.size;
        return c;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final Int2CharArrayMap this$0;
            {
                this.this$0 = int2CharArrayMap;
            }

            @Override
            public boolean contains(int n) {
                return Int2CharArrayMap.access$300(this.this$0, n) != -1;
            }

            @Override
            public boolean remove(int n) {
                int n2 = Int2CharArrayMap.access$300(this.this$0, n);
                if (n2 == -1) {
                    return true;
                }
                int n3 = Int2CharArrayMap.access$000(this.this$0) - n2 - 1;
                System.arraycopy(Int2CharArrayMap.access$100(this.this$0), n2 + 1, Int2CharArrayMap.access$100(this.this$0), n2, n3);
                System.arraycopy(Int2CharArrayMap.access$200(this.this$0), n2 + 1, Int2CharArrayMap.access$200(this.this$0), n2, n3);
                Int2CharArrayMap.access$010(this.this$0);
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
                        return this.pos < Int2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2CharArrayMap.access$100(this.this$1.this$0), this.pos, Int2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2CharArrayMap.access$200(this.this$1.this$0), this.pos, Int2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2CharArrayMap.access$000(this.this$0);
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
            final Int2CharArrayMap this$0;
            {
                this.this$0 = int2CharArrayMap;
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
                        return this.pos < Int2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Int2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Int2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Int2CharArrayMap.access$100(this.this$1.this$0), this.pos, Int2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Int2CharArrayMap.access$200(this.this$1.this$0), this.pos, Int2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Int2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Int2CharArrayMap.access$000(this.this$0);
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

    public Int2CharArrayMap clone() {
        Int2CharArrayMap int2CharArrayMap;
        try {
            int2CharArrayMap = (Int2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        int2CharArrayMap.key = (int[])this.key.clone();
        int2CharArrayMap.value = (char[])this.value.clone();
        return int2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeInt(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readInt();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet int2CharEntrySet() {
        return this.int2CharEntrySet();
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

    static int access$000(Int2CharArrayMap int2CharArrayMap) {
        return int2CharArrayMap.size;
    }

    static int[] access$100(Int2CharArrayMap int2CharArrayMap) {
        return int2CharArrayMap.key;
    }

    static char[] access$200(Int2CharArrayMap int2CharArrayMap) {
        return int2CharArrayMap.value;
    }

    static int access$010(Int2CharArrayMap int2CharArrayMap) {
        return int2CharArrayMap.size--;
    }

    static int access$300(Int2CharArrayMap int2CharArrayMap, int n) {
        return int2CharArrayMap.findKey(n);
    }

    private final class EntrySet
    extends AbstractObjectSet<Int2CharMap.Entry>
    implements Int2CharMap.FastEntrySet {
        final Int2CharArrayMap this$0;

        private EntrySet(Int2CharArrayMap int2CharArrayMap) {
            this.this$0 = int2CharArrayMap;
        }

        @Override
        public ObjectIterator<Int2CharMap.Entry> iterator() {
            return new ObjectIterator<Int2CharMap.Entry>(this){
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
                    return this.next < Int2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractInt2CharMap.BasicEntry(Int2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Int2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Int2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Int2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractInt2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractInt2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Int2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Int2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Int2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Int2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Int2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Int2CharArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            return this.this$0.containsKey(n) && this.this$0.get(n) == ((Character)entry.getValue()).charValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            int n = (Integer)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n2 = Int2CharArrayMap.access$300(this.this$0, n);
            if (n2 == -1 || c != Int2CharArrayMap.access$200(this.this$0)[n2]) {
                return true;
            }
            int n3 = Int2CharArrayMap.access$000(this.this$0) - n2 - 1;
            System.arraycopy(Int2CharArrayMap.access$100(this.this$0), n2 + 1, Int2CharArrayMap.access$100(this.this$0), n2, n3);
            System.arraycopy(Int2CharArrayMap.access$200(this.this$0), n2 + 1, Int2CharArrayMap.access$200(this.this$0), n2, n3);
            Int2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Int2CharArrayMap int2CharArrayMap, 1 var2_2) {
            this(int2CharArrayMap);
        }
    }
}

