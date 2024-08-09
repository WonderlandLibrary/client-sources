/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2CharMap;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
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
public class Char2CharArrayMap
extends AbstractChar2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient char[] value;
    private int size;

    public Char2CharArrayMap(char[] cArray, char[] cArray2) {
        this.key = cArray;
        this.value = cArray2;
        this.size = cArray.length;
        if (cArray.length != cArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + cArray2.length + ")");
        }
    }

    public Char2CharArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Char2CharArrayMap(int n) {
        this.key = new char[n];
        this.value = new char[n];
    }

    public Char2CharArrayMap(Char2CharMap char2CharMap) {
        this(char2CharMap.size());
        this.putAll(char2CharMap);
    }

    public Char2CharArrayMap(Map<? extends Character, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2CharArrayMap(char[] cArray, char[] cArray2, int n) {
        this.key = cArray;
        this.value = cArray2;
        this.size = n;
        if (cArray.length != cArray2.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + cArray2.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2CharMap.FastEntrySet char2CharEntrySet() {
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
    public char get(char c) {
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
    public char put(char c, char c2) {
        int n = this.findKey(c);
        if (n != -1) {
            char c3 = this.value[n];
            this.value[n] = c2;
            return c3;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            char[] cArray2 = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                cArray2[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = cArray2;
        }
        this.key[this.size] = c;
        this.value[this.size] = c2;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return this.defRetValue;
        }
        char c2 = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return c2;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2CharArrayMap this$0;
            {
                this.this$0 = char2CharArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2CharArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2CharArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2CharArrayMap.access$100(this.this$0), n + 1, Char2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2CharArrayMap.access$200(this.this$0), n + 1, Char2CharArrayMap.access$200(this.this$0), n, n2);
                Char2CharArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2CharArrayMap.access$100(this.this$1.this$0), this.pos, Char2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2CharArrayMap.access$200(this.this$1.this$0), this.pos, Char2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2CharArrayMap.access$000(this.this$0);
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
            final Char2CharArrayMap this$0;
            {
                this.this$0 = char2CharArrayMap;
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
                        return this.pos < Char2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2CharArrayMap.access$100(this.this$1.this$0), this.pos, Char2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2CharArrayMap.access$200(this.this$1.this$0), this.pos, Char2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2CharArrayMap.access$000(this.this$0);
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

    public Char2CharArrayMap clone() {
        Char2CharArrayMap char2CharArrayMap;
        try {
            char2CharArrayMap = (Char2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2CharArrayMap.key = (char[])this.key.clone();
        char2CharArrayMap.value = (char[])this.value.clone();
        return char2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet char2CharEntrySet() {
        return this.char2CharEntrySet();
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

    static int access$000(Char2CharArrayMap char2CharArrayMap) {
        return char2CharArrayMap.size;
    }

    static char[] access$100(Char2CharArrayMap char2CharArrayMap) {
        return char2CharArrayMap.key;
    }

    static char[] access$200(Char2CharArrayMap char2CharArrayMap) {
        return char2CharArrayMap.value;
    }

    static int access$010(Char2CharArrayMap char2CharArrayMap) {
        return char2CharArrayMap.size--;
    }

    static int access$300(Char2CharArrayMap char2CharArrayMap, char c) {
        return char2CharArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2CharMap.Entry>
    implements Char2CharMap.FastEntrySet {
        final Char2CharArrayMap this$0;

        private EntrySet(Char2CharArrayMap char2CharArrayMap) {
            this.this$0 = char2CharArrayMap;
        }

        @Override
        public ObjectIterator<Char2CharMap.Entry> iterator() {
            return new ObjectIterator<Char2CharMap.Entry>(this){
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
                    return this.next < Char2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Char2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Char2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2CharArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Character)entry.getValue()).charValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            char c2 = ((Character)entry.getValue()).charValue();
            int n = Char2CharArrayMap.access$300(this.this$0, c);
            if (n == -1 || c2 != Char2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2CharArrayMap.access$100(this.this$0), n + 1, Char2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2CharArrayMap.access$200(this.this$0), n + 1, Char2CharArrayMap.access$200(this.this$0), n, n2);
            Char2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2CharArrayMap char2CharArrayMap, 1 var2_2) {
            this(char2CharArrayMap);
        }
    }
}

