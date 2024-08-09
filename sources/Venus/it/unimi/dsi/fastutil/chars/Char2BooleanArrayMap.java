/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
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
public class Char2BooleanArrayMap
extends AbstractChar2BooleanMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient boolean[] value;
    private int size;

    public Char2BooleanArrayMap(char[] cArray, boolean[] blArray) {
        this.key = cArray;
        this.value = blArray;
        this.size = cArray.length;
        if (cArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + blArray.length + ")");
        }
    }

    public Char2BooleanArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = BooleanArrays.EMPTY_ARRAY;
    }

    public Char2BooleanArrayMap(int n) {
        this.key = new char[n];
        this.value = new boolean[n];
    }

    public Char2BooleanArrayMap(Char2BooleanMap char2BooleanMap) {
        this(char2BooleanMap.size());
        this.putAll(char2BooleanMap);
    }

    public Char2BooleanArrayMap(Map<? extends Character, ? extends Boolean> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2BooleanArrayMap(char[] cArray, boolean[] blArray, int n) {
        this.key = cArray;
        this.value = blArray;
        this.size = n;
        if (cArray.length != blArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + blArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2BooleanMap.FastEntrySet char2BooleanEntrySet() {
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
    public boolean get(char c) {
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
    public boolean put(char c, boolean bl) {
        int n = this.findKey(c);
        if (n != -1) {
            boolean bl2 = this.value[n];
            this.value[n] = bl;
            return bl2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            boolean[] blArray = new boolean[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                blArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = blArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = bl;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public boolean remove(char c) {
        int n = this.findKey(c);
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
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2BooleanArrayMap this$0;
            {
                this.this$0 = char2BooleanArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2BooleanArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2BooleanArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2BooleanArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2BooleanArrayMap.access$100(this.this$0), n + 1, Char2BooleanArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2BooleanArrayMap.access$200(this.this$0), n + 1, Char2BooleanArrayMap.access$200(this.this$0), n, n2);
                Char2BooleanArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2BooleanArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Char2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Char2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2BooleanArrayMap.access$000(this.this$0);
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
            final Char2BooleanArrayMap this$0;
            {
                this.this$0 = char2BooleanArrayMap;
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
                        return this.pos < Char2BooleanArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2BooleanArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2BooleanArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2BooleanArrayMap.access$100(this.this$1.this$0), this.pos, Char2BooleanArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2BooleanArrayMap.access$200(this.this$1.this$0), this.pos, Char2BooleanArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2BooleanArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2BooleanArrayMap.access$000(this.this$0);
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

    public Char2BooleanArrayMap clone() {
        Char2BooleanArrayMap char2BooleanArrayMap;
        try {
            char2BooleanArrayMap = (Char2BooleanArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2BooleanArrayMap.key = (char[])this.key.clone();
        char2BooleanArrayMap.value = (boolean[])this.value.clone();
        return char2BooleanArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeBoolean(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readBoolean();
        }
    }

    public ObjectSet char2BooleanEntrySet() {
        return this.char2BooleanEntrySet();
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

    static int access$000(Char2BooleanArrayMap char2BooleanArrayMap) {
        return char2BooleanArrayMap.size;
    }

    static char[] access$100(Char2BooleanArrayMap char2BooleanArrayMap) {
        return char2BooleanArrayMap.key;
    }

    static boolean[] access$200(Char2BooleanArrayMap char2BooleanArrayMap) {
        return char2BooleanArrayMap.value;
    }

    static int access$010(Char2BooleanArrayMap char2BooleanArrayMap) {
        return char2BooleanArrayMap.size--;
    }

    static int access$300(Char2BooleanArrayMap char2BooleanArrayMap, char c) {
        return char2BooleanArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2BooleanMap.Entry>
    implements Char2BooleanMap.FastEntrySet {
        final Char2BooleanArrayMap this$0;

        private EntrySet(Char2BooleanArrayMap char2BooleanArrayMap) {
            this.this$0 = char2BooleanArrayMap;
        }

        @Override
        public ObjectIterator<Char2BooleanMap.Entry> iterator() {
            return new ObjectIterator<Char2BooleanMap.Entry>(this){
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
                    return this.next < Char2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2BooleanMap.BasicEntry(Char2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr], Char2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2BooleanMap.Entry> fastIterator() {
            return new ObjectIterator<Char2BooleanMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2BooleanMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2BooleanMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2BooleanArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2BooleanMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2BooleanArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2BooleanArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2BooleanArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2BooleanArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2BooleanArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2BooleanArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2BooleanArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2BooleanArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == ((Boolean)entry.getValue()).booleanValue();
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Boolean)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            boolean bl = (Boolean)entry.getValue();
            int n = Char2BooleanArrayMap.access$300(this.this$0, c);
            if (n == -1 || bl != Char2BooleanArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2BooleanArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2BooleanArrayMap.access$100(this.this$0), n + 1, Char2BooleanArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2BooleanArrayMap.access$200(this.this$0), n + 1, Char2BooleanArrayMap.access$200(this.this$0), n, n2);
            Char2BooleanArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2BooleanArrayMap char2BooleanArrayMap, 1 var2_2) {
            this(char2BooleanArrayMap);
        }
    }
}

