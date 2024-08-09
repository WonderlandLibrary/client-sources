/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ReferenceMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public class Char2ReferenceArrayMap<V>
extends AbstractChar2ReferenceMap<V>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient Object[] value;
    private int size;

    public Char2ReferenceArrayMap(char[] cArray, Object[] objectArray) {
        this.key = cArray;
        this.value = objectArray;
        this.size = cArray.length;
        if (cArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + objectArray.length + ")");
        }
    }

    public Char2ReferenceArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Char2ReferenceArrayMap(int n) {
        this.key = new char[n];
        this.value = new Object[n];
    }

    public Char2ReferenceArrayMap(Char2ReferenceMap<V> char2ReferenceMap) {
        this(char2ReferenceMap.size());
        this.putAll(char2ReferenceMap);
    }

    public Char2ReferenceArrayMap(Map<? extends Character, ? extends V> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2ReferenceArrayMap(char[] cArray, Object[] objectArray, int n) {
        this.key = cArray;
        this.value = objectArray;
        this.size = n;
        if (cArray.length != objectArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + objectArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2ReferenceMap.FastEntrySet<V> char2ReferenceEntrySet() {
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
    public V get(char c) {
        char[] cArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (cArray[n] != c) continue;
            return (V)this.value[n];
        }
        return (V)this.defRetValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        int n = this.size;
        while (n-- != 0) {
            this.value[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(char c) {
        return this.findKey(c) != -1;
    }

    @Override
    public boolean containsValue(Object object) {
        int n = this.size;
        while (n-- != 0) {
            if (this.value[n] != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public V put(char c, V v) {
        int n = this.findKey(c);
        if (n != -1) {
            Object object = this.value[n];
            this.value[n] = v;
            return (V)object;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                objectArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = objectArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = v;
        ++this.size;
        return (V)this.defRetValue;
    }

    @Override
    public V remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return (V)this.defRetValue;
        }
        Object object = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.value[this.size] = null;
        return (V)object;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2ReferenceArrayMap this$0;
            {
                this.this$0 = char2ReferenceArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2ReferenceArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2ReferenceArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2ReferenceArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$0), n + 1, Char2ReferenceArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$0), n + 1, Char2ReferenceArrayMap.access$200(this.this$0), n, n2);
                Char2ReferenceArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ReferenceArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ReferenceArrayMap.access$000(this.this$0);
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
    public ReferenceCollection<V> values() {
        return new AbstractReferenceCollection<V>(this){
            final Char2ReferenceArrayMap this$0;
            {
                this.this$0 = char2ReferenceArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
            }

            @Override
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2ReferenceArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2ReferenceArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2ReferenceArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos, Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos, Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2ReferenceArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2ReferenceArrayMap.access$000(this.this$0);
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

    public Char2ReferenceArrayMap<V> clone() {
        Char2ReferenceArrayMap char2ReferenceArrayMap;
        try {
            char2ReferenceArrayMap = (Char2ReferenceArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2ReferenceArrayMap.key = (char[])this.key.clone();
        char2ReferenceArrayMap.value = (Object[])this.value.clone();
        return char2ReferenceArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readObject();
        }
    }

    @Override
    public ObjectSet char2ReferenceEntrySet() {
        return this.char2ReferenceEntrySet();
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

    static int access$000(Char2ReferenceArrayMap char2ReferenceArrayMap) {
        return char2ReferenceArrayMap.size;
    }

    static char[] access$100(Char2ReferenceArrayMap char2ReferenceArrayMap) {
        return char2ReferenceArrayMap.key;
    }

    static Object[] access$200(Char2ReferenceArrayMap char2ReferenceArrayMap) {
        return char2ReferenceArrayMap.value;
    }

    static int access$010(Char2ReferenceArrayMap char2ReferenceArrayMap) {
        return char2ReferenceArrayMap.size--;
    }

    static int access$300(Char2ReferenceArrayMap char2ReferenceArrayMap, char c) {
        return char2ReferenceArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2ReferenceMap.Entry<V>>
    implements Char2ReferenceMap.FastEntrySet<V> {
        final Char2ReferenceArrayMap this$0;

        private EntrySet(Char2ReferenceArrayMap char2ReferenceArrayMap) {
            this.this$0 = char2ReferenceArrayMap;
        }

        @Override
        public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
            return new ObjectIterator<Char2ReferenceMap.Entry<V>>(this){
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
                    return this.next < Char2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2ReferenceMap.BasicEntry<Object>(Char2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr], Char2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Char2ReferenceArrayMap.access$200((Char2ReferenceArrayMap)this.this$1.this$0)[Char2ReferenceArrayMap.access$000((Char2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Char2ReferenceMap.Entry<V>>(this){
                int next;
                int curr;
                final AbstractChar2ReferenceMap.BasicEntry<V> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2ReferenceMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2ReferenceArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2ReferenceMap.Entry<V> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2ReferenceArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2ReferenceArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2ReferenceArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2ReferenceArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2ReferenceArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Char2ReferenceArrayMap.access$200((Char2ReferenceArrayMap)this.this$1.this$0)[Char2ReferenceArrayMap.access$000((Char2ReferenceArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2ReferenceArrayMap.access$000(this.this$0);
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
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && this.this$0.get(c) == entry.getValue();
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
            char c = ((Character)entry.getKey()).charValue();
            Object v = entry.getValue();
            int n = Char2ReferenceArrayMap.access$300(this.this$0, c);
            if (n == -1 || v != Char2ReferenceArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Char2ReferenceArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2ReferenceArrayMap.access$100(this.this$0), n + 1, Char2ReferenceArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2ReferenceArrayMap.access$200(this.this$0), n + 1, Char2ReferenceArrayMap.access$200(this.this$0), n, n2);
            Char2ReferenceArrayMap.access$010(this.this$0);
            Char2ReferenceArrayMap.access$200((Char2ReferenceArrayMap)this.this$0)[Char2ReferenceArrayMap.access$000((Char2ReferenceArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2ReferenceArrayMap char2ReferenceArrayMap, 1 var2_2) {
            this(char2ReferenceArrayMap);
        }
    }
}

