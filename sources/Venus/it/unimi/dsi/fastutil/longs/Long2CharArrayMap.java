/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2CharMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2CharMap;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
public class Long2CharArrayMap
extends AbstractLong2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient long[] key;
    private transient char[] value;
    private int size;

    public Long2CharArrayMap(long[] lArray, char[] cArray) {
        this.key = lArray;
        this.value = cArray;
        this.size = lArray.length;
        if (lArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + cArray.length + ")");
        }
    }

    public Long2CharArrayMap() {
        this.key = LongArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Long2CharArrayMap(int n) {
        this.key = new long[n];
        this.value = new char[n];
    }

    public Long2CharArrayMap(Long2CharMap long2CharMap) {
        this(long2CharMap.size());
        this.putAll(long2CharMap);
    }

    public Long2CharArrayMap(Map<? extends Long, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Long2CharArrayMap(long[] lArray, char[] cArray, int n) {
        this.key = lArray;
        this.value = cArray;
        this.size = n;
        if (lArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + lArray.length + ", " + cArray.length + ")");
        }
        if (n > lArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + lArray.length + ")");
        }
    }

    public Long2CharMap.FastEntrySet long2CharEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
            return n;
        }
        return 1;
    }

    @Override
    public char get(long l) {
        long[] lArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (lArray[n] != l) continue;
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
    public boolean containsKey(long l) {
        return this.findKey(l) != -1;
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
    public char put(long l, char c) {
        int n = this.findKey(l);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            long[] lArray = new long[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                lArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = lArray;
            this.value = cArray;
        }
        this.key[this.size] = l;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(long l) {
        int n = this.findKey(l);
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
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final Long2CharArrayMap this$0;
            {
                this.this$0 = long2CharArrayMap;
            }

            @Override
            public boolean contains(long l) {
                return Long2CharArrayMap.access$300(this.this$0, l) != -1;
            }

            @Override
            public boolean remove(long l) {
                int n = Long2CharArrayMap.access$300(this.this$0, l);
                if (n == -1) {
                    return true;
                }
                int n2 = Long2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Long2CharArrayMap.access$100(this.this$0), n + 1, Long2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Long2CharArrayMap.access$200(this.this$0), n + 1, Long2CharArrayMap.access$200(this.this$0), n, n2);
                Long2CharArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public LongIterator iterator() {
                return new LongIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Long2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2CharArrayMap.access$100(this.this$1.this$0), this.pos, Long2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2CharArrayMap.access$200(this.this$1.this$0), this.pos, Long2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2CharArrayMap.access$000(this.this$0);
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
            final Long2CharArrayMap this$0;
            {
                this.this$0 = long2CharArrayMap;
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
                        return this.pos < Long2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Long2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Long2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Long2CharArrayMap.access$100(this.this$1.this$0), this.pos, Long2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Long2CharArrayMap.access$200(this.this$1.this$0), this.pos, Long2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Long2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Long2CharArrayMap.access$000(this.this$0);
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

    public Long2CharArrayMap clone() {
        Long2CharArrayMap long2CharArrayMap;
        try {
            long2CharArrayMap = (Long2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        long2CharArrayMap.key = (long[])this.key.clone();
        long2CharArrayMap.value = (char[])this.value.clone();
        return long2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeLong(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new long[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readLong();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet long2CharEntrySet() {
        return this.long2CharEntrySet();
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

    static int access$000(Long2CharArrayMap long2CharArrayMap) {
        return long2CharArrayMap.size;
    }

    static long[] access$100(Long2CharArrayMap long2CharArrayMap) {
        return long2CharArrayMap.key;
    }

    static char[] access$200(Long2CharArrayMap long2CharArrayMap) {
        return long2CharArrayMap.value;
    }

    static int access$010(Long2CharArrayMap long2CharArrayMap) {
        return long2CharArrayMap.size--;
    }

    static int access$300(Long2CharArrayMap long2CharArrayMap, long l) {
        return long2CharArrayMap.findKey(l);
    }

    private final class EntrySet
    extends AbstractObjectSet<Long2CharMap.Entry>
    implements Long2CharMap.FastEntrySet {
        final Long2CharArrayMap this$0;

        private EntrySet(Long2CharArrayMap long2CharArrayMap) {
            this.this$0 = long2CharArrayMap;
        }

        @Override
        public ObjectIterator<Long2CharMap.Entry> iterator() {
            return new ObjectIterator<Long2CharMap.Entry>(this){
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
                    return this.next < Long2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractLong2CharMap.BasicEntry(Long2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Long2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Long2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Long2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractLong2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractLong2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Long2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Long2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Long2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Long2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Long2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Long2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Long2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Long2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Long2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Long2CharArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            long l = (Long)entry.getKey();
            return this.this$0.containsKey(l) && this.this$0.get(l) == ((Character)entry.getValue()).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Long)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            long l = (Long)entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n = Long2CharArrayMap.access$300(this.this$0, l);
            if (n == -1 || c != Long2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Long2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Long2CharArrayMap.access$100(this.this$0), n + 1, Long2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Long2CharArrayMap.access$200(this.this$0), n + 1, Long2CharArrayMap.access$200(this.this$0), n, n2);
            Long2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Long2CharArrayMap long2CharArrayMap, 1 var2_2) {
            this(long2CharArrayMap);
        }
    }
}

