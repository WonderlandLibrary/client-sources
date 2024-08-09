/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2CharMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.Object2CharMap;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
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
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Object2CharArrayMap<K>
extends AbstractObject2CharMap<K>
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient char[] value;
    private int size;

    public Object2CharArrayMap(Object[] objectArray, char[] cArray) {
        this.key = objectArray;
        this.value = cArray;
        this.size = objectArray.length;
        if (objectArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + cArray.length + ")");
        }
    }

    public Object2CharArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Object2CharArrayMap(int n) {
        this.key = new Object[n];
        this.value = new char[n];
    }

    public Object2CharArrayMap(Object2CharMap<K> object2CharMap) {
        this(object2CharMap.size());
        this.putAll(object2CharMap);
    }

    public Object2CharArrayMap(Map<? extends K, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Object2CharArrayMap(Object[] objectArray, char[] cArray, int n) {
        this.key = objectArray;
        this.value = cArray;
        this.size = n;
        if (objectArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + objectArray.length + ", " + cArray.length + ")");
        }
        if (n > objectArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + objectArray.length + ")");
        }
    }

    public Object2CharMap.FastEntrySet<K> object2CharEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public char getChar(Object object) {
        Object[] objectArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (!Objects.equals(objectArray[n], object)) continue;
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
        int n = this.size;
        while (n-- != 0) {
            this.key[n] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findKey(object) != -1;
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
    public char put(K k, char c) {
        int n = this.findKey(k);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            Object[] objectArray = new Object[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                objectArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = objectArray;
            this.value = cArray;
        }
        this.key[this.size] = k;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char removeChar(Object object) {
        int n = this.findKey(object);
        if (n == -1) {
            return this.defRetValue;
        }
        char c = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        this.key[this.size] = null;
        return c;
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final Object2CharArrayMap this$0;
            {
                this.this$0 = object2CharArrayMap;
            }

            @Override
            public boolean contains(Object object) {
                return Object2CharArrayMap.access$300(this.this$0, object) != -1;
            }

            @Override
            public boolean remove(Object object) {
                int n = Object2CharArrayMap.access$300(this.this$0, object);
                if (n == -1) {
                    return true;
                }
                int n2 = Object2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Object2CharArrayMap.access$100(this.this$0), n + 1, Object2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Object2CharArrayMap.access$200(this.this$0), n + 1, Object2CharArrayMap.access$200(this.this$0), n, n2);
                Object2CharArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Object2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2CharArrayMap.access$100(this.this$1.this$0), this.pos, Object2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2CharArrayMap.access$200(this.this$1.this$0), this.pos, Object2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2CharArrayMap.access$000(this.this$0);
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
            final Object2CharArrayMap this$0;
            {
                this.this$0 = object2CharArrayMap;
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
                        return this.pos < Object2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Object2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Object2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Object2CharArrayMap.access$100(this.this$1.this$0), this.pos, Object2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Object2CharArrayMap.access$200(this.this$1.this$0), this.pos, Object2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Object2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Object2CharArrayMap.access$000(this.this$0);
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

    public Object2CharArrayMap<K> clone() {
        Object2CharArrayMap object2CharArrayMap;
        try {
            object2CharArrayMap = (Object2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        object2CharArrayMap.key = (Object[])this.key.clone();
        object2CharArrayMap.value = (char[])this.value.clone();
        return object2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readObject();
            this.value[i] = objectInputStream.readChar();
        }
    }

    @Override
    public ObjectSet object2CharEntrySet() {
        return this.object2CharEntrySet();
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

    static int access$000(Object2CharArrayMap object2CharArrayMap) {
        return object2CharArrayMap.size;
    }

    static Object[] access$100(Object2CharArrayMap object2CharArrayMap) {
        return object2CharArrayMap.key;
    }

    static char[] access$200(Object2CharArrayMap object2CharArrayMap) {
        return object2CharArrayMap.value;
    }

    static int access$010(Object2CharArrayMap object2CharArrayMap) {
        return object2CharArrayMap.size--;
    }

    static int access$300(Object2CharArrayMap object2CharArrayMap, Object object) {
        return object2CharArrayMap.findKey(object);
    }

    private final class EntrySet
    extends AbstractObjectSet<Object2CharMap.Entry<K>>
    implements Object2CharMap.FastEntrySet<K> {
        final Object2CharArrayMap this$0;

        private EntrySet(Object2CharArrayMap object2CharArrayMap) {
            this.this$0 = object2CharArrayMap;
        }

        @Override
        public ObjectIterator<Object2CharMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2CharMap.Entry<K>>(this){
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
                    return this.next < Object2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2CharMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractObject2CharMap.BasicEntry<Object>(Object2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Object2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2CharArrayMap.access$100((Object2CharArrayMap)this.this$1.this$0)[Object2CharArrayMap.access$000((Object2CharArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Object2CharMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2CharMap.Entry<K>>(this){
                int next;
                int curr;
                final AbstractObject2CharMap.BasicEntry<K> entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractObject2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Object2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Object2CharMap.Entry<K> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Object2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Object2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Object2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2CharArrayMap.access$100((Object2CharArrayMap)this.this$1.this$0)[Object2CharArrayMap.access$000((Object2CharArrayMap)this.this$1.this$0)] = null;
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Object2CharArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            Object k = entry.getKey();
            return this.this$0.containsKey(k) && this.this$0.getChar(k) == ((Character)entry.getValue()).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            Object k = entry.getKey();
            char c = ((Character)entry.getValue()).charValue();
            int n = Object2CharArrayMap.access$300(this.this$0, k);
            if (n == -1 || c != Object2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Object2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Object2CharArrayMap.access$100(this.this$0), n + 1, Object2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Object2CharArrayMap.access$200(this.this$0), n + 1, Object2CharArrayMap.access$200(this.this$0), n, n2);
            Object2CharArrayMap.access$010(this.this$0);
            Object2CharArrayMap.access$100((Object2CharArrayMap)this.this$0)[Object2CharArrayMap.access$000((Object2CharArrayMap)this.this$0)] = null;
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Object2CharArrayMap object2CharArrayMap, 1 var2_2) {
            this(object2CharArrayMap);
        }
    }
}

