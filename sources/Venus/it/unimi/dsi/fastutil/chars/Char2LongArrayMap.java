/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
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
public class Char2LongArrayMap
extends AbstractChar2LongMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient long[] value;
    private int size;

    public Char2LongArrayMap(char[] cArray, long[] lArray) {
        this.key = cArray;
        this.value = lArray;
        this.size = cArray.length;
        if (cArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + lArray.length + ")");
        }
    }

    public Char2LongArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = LongArrays.EMPTY_ARRAY;
    }

    public Char2LongArrayMap(int n) {
        this.key = new char[n];
        this.value = new long[n];
    }

    public Char2LongArrayMap(Char2LongMap char2LongMap) {
        this(char2LongMap.size());
        this.putAll(char2LongMap);
    }

    public Char2LongArrayMap(Map<? extends Character, ? extends Long> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2LongArrayMap(char[] cArray, long[] lArray, int n) {
        this.key = cArray;
        this.value = lArray;
        this.size = n;
        if (cArray.length != lArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + lArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2LongMap.FastEntrySet char2LongEntrySet() {
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
    public long get(char c) {
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
    public boolean containsValue(long l) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public long put(char c, long l) {
        int n = this.findKey(c);
        if (n != -1) {
            long l2 = this.value[n];
            this.value[n] = l;
            return l2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                lArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = lArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = l;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public long remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return this.defRetValue;
        }
        long l = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return l;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2LongArrayMap this$0;
            {
                this.this$0 = char2LongArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2LongArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2LongArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2LongArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2LongArrayMap.access$100(this.this$0), n + 1, Char2LongArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2LongArrayMap.access$200(this.this$0), n + 1, Char2LongArrayMap.access$200(this.this$0), n, n2);
                Char2LongArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2LongArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2LongArrayMap.access$100(this.this$1.this$0), this.pos, Char2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2LongArrayMap.access$200(this.this$1.this$0), this.pos, Char2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2LongArrayMap.access$000(this.this$0);
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
    public LongCollection values() {
        return new AbstractLongCollection(this){
            final Char2LongArrayMap this$0;
            {
                this.this$0 = char2LongArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsValue(l);
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2LongArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2LongArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2LongArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2LongArrayMap.access$100(this.this$1.this$0), this.pos, Char2LongArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2LongArrayMap.access$200(this.this$1.this$0), this.pos, Char2LongArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2LongArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2LongArrayMap.access$000(this.this$0);
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

    public Char2LongArrayMap clone() {
        Char2LongArrayMap char2LongArrayMap;
        try {
            char2LongArrayMap = (Char2LongArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2LongArrayMap.key = (char[])this.key.clone();
        char2LongArrayMap.value = (long[])this.value.clone();
        return char2LongArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeLong(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new long[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readLong();
        }
    }

    public ObjectSet char2LongEntrySet() {
        return this.char2LongEntrySet();
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

    static int access$000(Char2LongArrayMap char2LongArrayMap) {
        return char2LongArrayMap.size;
    }

    static char[] access$100(Char2LongArrayMap char2LongArrayMap) {
        return char2LongArrayMap.key;
    }

    static long[] access$200(Char2LongArrayMap char2LongArrayMap) {
        return char2LongArrayMap.value;
    }

    static int access$010(Char2LongArrayMap char2LongArrayMap) {
        return char2LongArrayMap.size--;
    }

    static int access$300(Char2LongArrayMap char2LongArrayMap, char c) {
        return char2LongArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2LongMap.Entry>
    implements Char2LongMap.FastEntrySet {
        final Char2LongArrayMap this$0;

        private EntrySet(Char2LongArrayMap char2LongArrayMap) {
            this.this$0 = char2LongArrayMap;
        }

        @Override
        public ObjectIterator<Char2LongMap.Entry> iterator() {
            return new ObjectIterator<Char2LongMap.Entry>(this){
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
                    return this.next < Char2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2LongMap.BasicEntry(Char2LongArrayMap.access$100(this.this$1.this$0)[this.curr], Char2LongArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2LongMap.Entry> fastIterator() {
            return new ObjectIterator<Char2LongMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2LongMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2LongMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2LongArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2LongMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2LongArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2LongArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2LongArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2LongArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2LongArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2LongArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2LongArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2LongArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Long)entry.getValue()).longValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Long)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            long l = (Long)entry.getValue();
            int n = Char2LongArrayMap.access$300(this.this$0, c);
            if (n == -1 || l != Char2LongArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2LongArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2LongArrayMap.access$100(this.this$0), n + 1, Char2LongArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2LongArrayMap.access$200(this.this$0), n + 1, Char2LongArrayMap.access$200(this.this$0), n, n2);
            Char2LongArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2LongArrayMap char2LongArrayMap, 1 var2_2) {
            this(char2LongArrayMap);
        }
    }
}

