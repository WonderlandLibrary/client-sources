/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.shorts.AbstractShortList;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ShortArrayList
extends AbstractShortList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient short[] a;
    protected int size;
    static final boolean $assertionsDisabled = !ShortArrayList.class.desiredAssertionStatus();

    protected ShortArrayList(short[] sArray, boolean bl) {
        this.a = sArray;
    }

    public ShortArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? ShortArrays.EMPTY_ARRAY : new short[n];
    }

    public ShortArrayList() {
        this.a = ShortArrays.DEFAULT_EMPTY_ARRAY;
    }

    public ShortArrayList(Collection<? extends Short> collection) {
        this(collection.size());
        this.size = ShortIterators.unwrap(ShortIterators.asShortIterator(collection.iterator()), this.a);
    }

    public ShortArrayList(ShortCollection shortCollection) {
        this(shortCollection.size());
        this.size = ShortIterators.unwrap(shortCollection.iterator(), this.a);
    }

    public ShortArrayList(ShortList shortList) {
        this(shortList.size());
        this.size = shortList.size();
        shortList.getElements(0, this.a, 0, this.size);
    }

    public ShortArrayList(short[] sArray) {
        this(sArray, 0, sArray.length);
    }

    public ShortArrayList(short[] sArray, int n, int n2) {
        this(n2);
        System.arraycopy(sArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public ShortArrayList(Iterator<? extends Short> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((short)iterator2.next());
        }
    }

    public ShortArrayList(ShortIterator shortIterator) {
        this();
        while (shortIterator.hasNext()) {
            this.add(shortIterator.nextShort());
        }
    }

    public short[] elements() {
        return this.a;
    }

    public static ShortArrayList wrap(short[] sArray, int n) {
        if (n > sArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + sArray.length + ")");
        }
        ShortArrayList shortArrayList = new ShortArrayList(sArray, false);
        shortArrayList.size = n;
        return shortArrayList;
    }

    public static ShortArrayList wrap(short[] sArray) {
        return ShortArrayList.wrap(sArray, sArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == ShortArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = ShortArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != ShortArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = ShortArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, short s) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = s;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(short s) {
        this.grow(this.size + 1);
        this.a[this.size++] = s;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public short getShort(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(short s) {
        for (int i = 0; i < this.size; ++i) {
            if (s != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(short s) {
        int n = this.size;
        while (n-- != 0) {
            if (s != this.a[n]) continue;
            return n;
        }
        return 1;
    }

    @Override
    public short removeShort(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        short s = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return s;
    }

    @Override
    public boolean rem(short s) {
        int n = this.indexOf(s);
        if (n == -1) {
            return true;
        }
        this.removeShort(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public short set(int n, short s) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        short s2 = this.a[n];
        this.a[n] = s;
        return s2;
    }

    @Override
    public void clear() {
        this.size = 0;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void size(int n) {
        if (n > this.a.length) {
            this.ensureCapacity(n);
        }
        if (n > this.size) {
            java.util.Arrays.fill(this.a, this.size, n, (short)0);
        }
        this.size = n;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        this.trim(0);
    }

    public void trim(int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        short[] sArray = new short[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, sArray, 0, this.size);
        this.a = sArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, short[] sArray, int n2, int n3) {
        ShortArrays.ensureOffsetLength(sArray, n2, n3);
        System.arraycopy(this.a, n, sArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, short[] sArray, int n2, int n3) {
        this.ensureIndex(n);
        ShortArrays.ensureOffsetLength(sArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(sArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public short[] toArray(short[] sArray) {
        if (sArray == null || sArray.length < this.size) {
            sArray = new short[this.size];
        }
        System.arraycopy(this.a, 0, sArray, 0, this.size);
        return sArray;
    }

    @Override
    public boolean addAll(int n, ShortCollection shortCollection) {
        this.ensureIndex(n);
        int n2 = shortCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        ShortIterator shortIterator = shortCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = shortIterator.nextShort();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, ShortList shortList) {
        this.ensureIndex(n);
        int n2 = shortList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        shortList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(ShortCollection shortCollection) {
        int n;
        short[] sArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (shortCollection.contains(sArray[n])) continue;
            sArray[n2++] = sArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        short[] sArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(sArray[n])) continue;
            sArray[n2++] = sArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public ShortListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new ShortListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final ShortArrayList this$0;
            {
                this.this$0 = shortArrayList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public short nextShort() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public short previousShort() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.a[this.pos];
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(short s) {
                this.this$0.add(this.pos++, s);
                this.last = -1;
            }

            @Override
            public void set(short s) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, s);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeShort(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public ShortArrayList clone() {
        ShortArrayList shortArrayList = new ShortArrayList(this.size);
        System.arraycopy(this.a, 0, shortArrayList.a, 0, this.size);
        shortArrayList.size = this.size;
        return shortArrayList;
    }

    public boolean equals(ShortArrayList shortArrayList) {
        if (shortArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != shortArrayList.size()) {
            return true;
        }
        short[] sArray = this.a;
        short[] sArray2 = shortArrayList.a;
        while (n-- != 0) {
            if (sArray[n] == sArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ShortArrayList shortArrayList) {
        int n;
        int n2 = this.size();
        int n3 = shortArrayList.size();
        short[] sArray = this.a;
        short[] sArray2 = shortArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            short s = sArray[n];
            short s2 = sArray2[n];
            int n4 = Short.compare(s, s2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeShort(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new short[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readShort();
        }
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

