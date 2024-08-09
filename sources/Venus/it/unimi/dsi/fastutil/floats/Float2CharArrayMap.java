/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public class Float2CharArrayMap
extends AbstractFloat2CharMap
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] key;
    private transient char[] value;
    private int size;

    public Float2CharArrayMap(float[] fArray, char[] cArray) {
        this.key = fArray;
        this.value = cArray;
        this.size = fArray.length;
        if (fArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + cArray.length + ")");
        }
    }

    public Float2CharArrayMap() {
        this.key = FloatArrays.EMPTY_ARRAY;
        this.value = CharArrays.EMPTY_ARRAY;
    }

    public Float2CharArrayMap(int n) {
        this.key = new float[n];
        this.value = new char[n];
    }

    public Float2CharArrayMap(Float2CharMap float2CharMap) {
        this(float2CharMap.size());
        this.putAll(float2CharMap);
    }

    public Float2CharArrayMap(Map<? extends Float, ? extends Character> map) {
        this(map.size());
        this.putAll(map);
    }

    public Float2CharArrayMap(float[] fArray, char[] cArray, int n) {
        this.key = fArray;
        this.value = cArray;
        this.size = n;
        if (fArray.length != cArray.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + fArray.length + ", " + cArray.length + ")");
        }
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the backing-arrays size (" + fArray.length + ")");
        }
    }

    public Float2CharMap.FastEntrySet float2CharEntrySet() {
        return new EntrySet(this, null);
    }

    private int findKey(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public char get(float f) {
        float[] fArray = this.key;
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) != Float.floatToIntBits(f)) continue;
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
    public boolean containsKey(float f) {
        return this.findKey(f) != -1;
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
    public char put(float f, char c) {
        int n = this.findKey(f);
        if (n != -1) {
            char c2 = this.value[n];
            this.value[n] = c;
            return c2;
        }
        if (this.size == this.key.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.key[n2];
                cArray[n2] = this.value[n2];
            }
            this.key = fArray;
            this.value = cArray;
        }
        this.key[this.size] = f;
        this.value[this.size] = c;
        ++this.size;
        return this.defRetValue;
    }

    @Override
    public char remove(float f) {
        int n = this.findKey(f);
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
    public FloatSet keySet() {
        return new AbstractFloatSet(this){
            final Float2CharArrayMap this$0;
            {
                this.this$0 = float2CharArrayMap;
            }

            @Override
            public boolean contains(float f) {
                return Float2CharArrayMap.access$300(this.this$0, f) != -1;
            }

            @Override
            public boolean remove(float f) {
                int n = Float2CharArrayMap.access$300(this.this$0, f);
                if (n == -1) {
                    return true;
                }
                int n2 = Float2CharArrayMap.access$000(this.this$0) - n - 1;
                System.arraycopy(Float2CharArrayMap.access$100(this.this$0), n + 1, Float2CharArrayMap.access$100(this.this$0), n, n2);
                System.arraycopy(Float2CharArrayMap.access$200(this.this$0), n + 1, Float2CharArrayMap.access$200(this.this$0), n, n2);
                Float2CharArrayMap.access$010(this.this$0);
                return false;
            }

            @Override
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    int pos;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.pos = 0;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.pos < Float2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2CharArrayMap.access$100(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2CharArrayMap.access$100(this.this$1.this$0), this.pos, Float2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2CharArrayMap.access$200(this.this$1.this$0), this.pos, Float2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2CharArrayMap.access$000(this.this$0);
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
            final Float2CharArrayMap this$0;
            {
                this.this$0 = float2CharArrayMap;
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
                        return this.pos < Float2CharArrayMap.access$000(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return Float2CharArrayMap.access$200(this.this$1.this$0)[this.pos++];
                    }

                    @Override
                    public void remove() {
                        if (this.pos == 0) {
                            throw new IllegalStateException();
                        }
                        int n = Float2CharArrayMap.access$000(this.this$1.this$0) - this.pos;
                        System.arraycopy(Float2CharArrayMap.access$100(this.this$1.this$0), this.pos, Float2CharArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                        System.arraycopy(Float2CharArrayMap.access$200(this.this$1.this$0), this.pos, Float2CharArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                        Float2CharArrayMap.access$010(this.this$1.this$0);
                    }
                };
            }

            @Override
            public int size() {
                return Float2CharArrayMap.access$000(this.this$0);
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

    public Float2CharArrayMap clone() {
        Float2CharArrayMap float2CharArrayMap;
        try {
            float2CharArrayMap = (Float2CharArrayMap)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        float2CharArrayMap.key = (float[])this.key.clone();
        float2CharArrayMap.value = (char[])this.value.clone();
        return float2CharArrayMap;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.key[i]);
            objectOutputStream.writeChar(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new float[this.size];
        this.value = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.key[i] = objectInputStream.readFloat();
            this.value[i] = objectInputStream.readChar();
        }
    }

    public ObjectSet float2CharEntrySet() {
        return this.float2CharEntrySet();
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

    static int access$000(Float2CharArrayMap float2CharArrayMap) {
        return float2CharArrayMap.size;
    }

    static float[] access$100(Float2CharArrayMap float2CharArrayMap) {
        return float2CharArrayMap.key;
    }

    static char[] access$200(Float2CharArrayMap float2CharArrayMap) {
        return float2CharArrayMap.value;
    }

    static int access$010(Float2CharArrayMap float2CharArrayMap) {
        return float2CharArrayMap.size--;
    }

    static int access$300(Float2CharArrayMap float2CharArrayMap, float f) {
        return float2CharArrayMap.findKey(f);
    }

    private final class EntrySet
    extends AbstractObjectSet<Float2CharMap.Entry>
    implements Float2CharMap.FastEntrySet {
        final Float2CharArrayMap this$0;

        private EntrySet(Float2CharArrayMap float2CharArrayMap) {
            this.this$0 = float2CharArrayMap;
        }

        @Override
        public ObjectIterator<Float2CharMap.Entry> iterator() {
            return new ObjectIterator<Float2CharMap.Entry>(this){
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
                    return this.next < Float2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    return new AbstractFloat2CharMap.BasicEntry(Float2CharArrayMap.access$100(this.this$1.this$0)[this.curr], Float2CharArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public ObjectIterator<Float2CharMap.Entry> fastIterator() {
            return new ObjectIterator<Float2CharMap.Entry>(this){
                int next;
                int curr;
                final AbstractFloat2CharMap.BasicEntry entry;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.next = 0;
                    this.curr = -1;
                    this.entry = new AbstractFloat2CharMap.BasicEntry();
                }

                @Override
                public boolean hasNext() {
                    return this.next < Float2CharArrayMap.access$000(this.this$1.this$0);
                }

                @Override
                public Float2CharMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.curr = this.next;
                    this.entry.key = Float2CharArrayMap.access$100(this.this$1.this$0)[this.curr];
                    this.entry.value = Float2CharArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }

                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int n = Float2CharArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Float2CharArrayMap.access$100(this.this$1.this$0), this.next + 1, Float2CharArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Float2CharArrayMap.access$200(this.this$1.this$0), this.next + 1, Float2CharArrayMap.access$200(this.this$1.this$0), this.next, n);
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return Float2CharArrayMap.access$000(this.this$0);
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            return this.this$0.containsKey(f) && this.this$0.get(f) == ((Character)entry.getValue()).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            if (entry.getKey() == null || !(entry.getKey() instanceof Float)) {
                return true;
            }
            if (entry.getValue() == null || !(entry.getValue() instanceof Character)) {
                return true;
            }
            float f = ((Float)entry.getKey()).floatValue();
            char c = ((Character)entry.getValue()).charValue();
            int n = Float2CharArrayMap.access$300(this.this$0, f);
            if (n == -1 || c != Float2CharArrayMap.access$200(this.this$0)[n]) {
                return true;
            }
            int n2 = Float2CharArrayMap.access$000(this.this$0) - n - 1;
            System.arraycopy(Float2CharArrayMap.access$100(this.this$0), n + 1, Float2CharArrayMap.access$100(this.this$0), n, n2);
            System.arraycopy(Float2CharArrayMap.access$200(this.this$0), n + 1, Float2CharArrayMap.access$200(this.this$0), n, n2);
            Float2CharArrayMap.access$010(this.this$0);
            return false;
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        EntrySet(Float2CharArrayMap float2CharArrayMap, 1 var2_2) {
            this(float2CharArrayMap);
        }
    }
}

