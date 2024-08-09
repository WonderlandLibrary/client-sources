/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
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
public class Char2FloatArrayMap
extends AbstractChar2FloatMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] key;
    private transient float[] value;
    private int size;

    public Char2FloatArrayMap(char[] cArray, float[] fArray) {
        this.key = cArray;
        this.value = fArray;
        this.size = cArray.length;
        if (cArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + fArray.length + ")");
        }
    }

    public Char2FloatArrayMap() {
        this.key = CharArrays.EMPTY_ARRAY;
        this.value = FloatArrays.EMPTY_ARRAY;
    }

    public Char2FloatArrayMap(int n) {
        this.key = new char[n];
        this.value = new float[n];
    }

    public Char2FloatArrayMap(Char2FloatMap char2FloatMap) {
        this(char2FloatMap.size());
        this.putAll(char2FloatMap);
    }

    public Char2FloatArrayMap(Map<? extends Character, ? extends Float> map) {
        this(map.size());
        this.putAll(map);
    }

    public Char2FloatArrayMap(char[] cArray, float[] fArray, int n) {
        this.key = cArray;
        this.value = fArray;
        this.size = n;
        if (cArray.length != fArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + cArray.length + ", " + fArray.length + ")");
        }
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + cArray.length + ")");
        }
    }

    public Char2FloatMap.FastEntrySet char2FloatEntrySet() {
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
    public float get(char c) {
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
    public boolean containsValue(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(this.value[n]) != Float.floatToIntBits(f)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public float put(char c, float f) {
        int n = this.findKey(c);
        if (n != -1) {
            float f2 = this.value[n];
            this.value[n] = f;
            return f2;
        }
        if (this.size == this.key.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.key[n2];
                fArray[n2] = this.value[n2];
            }
            this.key = cArray;
            this.value = fArray;
        }
        this.key[this.size] = c;
        this.value[this.size] = f;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public float remove(char c) {
        int n = this.findKey(c);
        if (n == -1) {
            return this.defRetValue;
        }
        float f = this.value[n];
        int n2 = this.size - n - 1;
        System.arraycopy(this.key, n + 1, this.key, n, n2);
        System.arraycopy(this.value, n + 1, this.value, n, n2);
        --this.size;
        return f;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final Char2FloatArrayMap this$0;
            {
                this.this$0 = char2FloatArrayMap;
            }

            @Override
            public boolean contains(char c) {
                return Char2FloatArrayMap.access$300(this.this$0, c) != -1;
            }

            @Override
            public boolean remove(char c) {
                int n = Char2FloatArrayMap.access$300(this.this$0, c);
                if (n == -1) {
                    return true;
                }
                int n2 = Char2FloatArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Char2FloatArrayMap.access$100(this.this$0), n + 1, Char2FloatArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Char2FloatArrayMap.access$200(this.this$0), n + 1, Char2FloatArrayMap.access$200(this.this$0), n, n2);
                Char2FloatArrayMap.access$010(this.this$0);
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
                        return this.pos < Char2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2FloatArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Char2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Char2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2FloatArrayMap.access$000(this.this$0);
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final Char2FloatArrayMap this$0;
            {
                this.this$0 = char2FloatArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Char2FloatArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Char2FloatArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Char2FloatArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Char2FloatArrayMap.access$100(this.this$1.this$0), this.pos, Char2FloatArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Char2FloatArrayMap.access$200(this.this$1.this$0), this.pos, Char2FloatArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Char2FloatArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Char2FloatArrayMap.access$000(this.this$0);
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

    public Char2FloatArrayMap clone() {
        Char2FloatArrayMap char2FloatArrayMap;
        try {
            char2FloatArrayMap = (Char2FloatArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        char2FloatArrayMap.key = (char[])this.key.clone();
        char2FloatArrayMap.value = (float[])this.value.clone();
        return char2FloatArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.key[i]);
            objectOutputStream.writeFloat(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new char[this.size];
        this.value = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readChar();
            this.value[i] = objectInputStream.readFloat();
        }
    }

    public ObjectSet char2FloatEntrySet() {
        return this.char2FloatEntrySet();
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

    static int access$000(Char2FloatArrayMap char2FloatArrayMap) {
        return char2FloatArrayMap.size;
    }

    static char[] access$100(Char2FloatArrayMap char2FloatArrayMap) {
        return char2FloatArrayMap.key;
    }

    static float[] access$200(Char2FloatArrayMap char2FloatArrayMap) {
        return char2FloatArrayMap.value;
    }

    static int access$010(Char2FloatArrayMap char2FloatArrayMap) {
        return char2FloatArrayMap.size--;
    }

    static int access$300(Char2FloatArrayMap char2FloatArrayMap, char c) {
        return char2FloatArrayMap.findKey(c);
    }

    private final class EntrySet
    extends AbstractObjectSet<Char2FloatMap.Entry>
    implements Char2FloatMap.FastEntrySet {
        final Char2FloatArrayMap this$0;

        private EntrySet(Char2FloatArrayMap char2FloatArrayMap) {
            this.this$0 = char2FloatArrayMap;
        }

        @Override
        public ObjectIterator<Char2FloatMap.Entry> iterator() {
            return new ObjectIterator<Char2FloatMap.Entry>(this){
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
                    return this.next < Char2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractChar2FloatMap.BasicEntry(Char2FloatArrayMap.access$100(this.this$1.this$0)[this.curr], Char2FloatArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Char2FloatMap.Entry> fastIterator() {
            return new ObjectIterator<Char2FloatMap.Entry>(this){
                int next;
                int curr;
                final AbstractChar2FloatMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractChar2FloatMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Char2FloatArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Char2FloatMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Char2FloatArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Char2FloatArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Char2FloatArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Char2FloatArrayMap.access$100(this.this$1.this$0), this.next + 1, Char2FloatArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Char2FloatArrayMap.access$200(this.this$1.this$0), this.next + 1, Char2FloatArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Char2FloatArrayMap.access$000(this.this$0);
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            return this.this$0.containsKey(c) && Float.floatToIntBits(this.this$0.get(c)) == Float.floatToIntBits(((Float)entry.getValue()).floatValue());
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Float)) {
                return true;
            }
            char c = ((Character)entry.getKey()).charValue();
            float f = ((Float)entry.getValue()).floatValue();
            int n = Char2FloatArrayMap.access$300(this.this$0, c);
            if (n == -1 || Float.floatToIntBits(f) != Float.floatToIntBits(Char2FloatArrayMap.access$200(this.this$0)[n])) {
                return true;
            }
            int n2 = Char2FloatArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Char2FloatArrayMap.access$100(this.this$0), n + 1, Char2FloatArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Char2FloatArrayMap.access$200(this.this$0), n + 1, Char2FloatArrayMap.access$200(this.this$0), n, n2);
            Char2FloatArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Char2FloatArrayMap char2FloatArrayMap, 1 var2_2) {
            this(char2FloatArrayMap);
        }
    }
}

