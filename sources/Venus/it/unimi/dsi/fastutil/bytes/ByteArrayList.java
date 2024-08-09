/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.bytes.AbstractByteList;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
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
public class ByteArrayList
extends AbstractByteList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient byte[] a;
    protected int size;
    static final boolean $assertionsDisabled = !ByteArrayList.class.desiredAssertionStatus();

    protected ByteArrayList(byte[] byArray, boolean bl) {
        this.a = byArray;
    }

    public ByteArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? ByteArrays.EMPTY_ARRAY : new byte[n];
    }

    public ByteArrayList() {
        this.a = ByteArrays.DEFAULT_EMPTY_ARRAY;
    }

    public ByteArrayList(Collection<? extends Byte> collection) {
        this(collection.size());
        this.size = ByteIterators.unwrap(ByteIterators.asByteIterator(collection.iterator()), this.a);
    }

    public ByteArrayList(ByteCollection byteCollection) {
        this(byteCollection.size());
        this.size = ByteIterators.unwrap(byteCollection.iterator(), this.a);
    }

    public ByteArrayList(ByteList byteList) {
        this(byteList.size());
        this.size = byteList.size();
        byteList.getElements(0, this.a, 0, this.size);
    }

    public ByteArrayList(byte[] byArray) {
        this(byArray, 0, byArray.length);
    }

    public ByteArrayList(byte[] byArray, int n, int n2) {
        this(n2);
        System.arraycopy(byArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public ByteArrayList(Iterator<? extends Byte> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((byte)iterator2.next());
        }
    }

    public ByteArrayList(ByteIterator byteIterator) {
        this();
        while (byteIterator.hasNext()) {
            this.add(byteIterator.nextByte());
        }
    }

    public byte[] elements() {
        return this.a;
    }

    public static ByteArrayList wrap(byte[] byArray, int n) {
        if (n > byArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + byArray.length + ")");
        }
        ByteArrayList byteArrayList = new ByteArrayList(byArray, false);
        byteArrayList.size = n;
        return byteArrayList;
    }

    public static ByteArrayList wrap(byte[] byArray) {
        return ByteArrayList.wrap(byArray, byArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == ByteArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = ByteArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != ByteArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = ByteArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, byte by) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = by;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(byte by) {
        this.grow(this.size + 1);
        this.a[this.size++] = by;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public byte getByte(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(byte by) {
        for (int i = 0; i < this.size; ++i) {
            if (by != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (by != this.a[n]) continue;
            return n;
        }
        return 1;
    }

    @Override
    public byte removeByte(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        byte by = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return by;
    }

    @Override
    public boolean rem(byte by) {
        int n = this.indexOf(by);
        if (n == -1) {
            return true;
        }
        this.removeByte(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public byte set(int n, byte by) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        byte by2 = this.a[n];
        this.a[n] = by;
        return by2;
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
            java.util.Arrays.fill(this.a, this.size, n, (byte)0);
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
        byte[] byArray = new byte[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, byArray, 0, this.size);
        this.a = byArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, byte[] byArray, int n2, int n3) {
        ByteArrays.ensureOffsetLength(byArray, n2, n3);
        System.arraycopy(this.a, n, byArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, byte[] byArray, int n2, int n3) {
        this.ensureIndex(n);
        ByteArrays.ensureOffsetLength(byArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(byArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public byte[] toArray(byte[] byArray) {
        if (byArray == null || byArray.length < this.size) {
            byArray = new byte[this.size];
        }
        System.arraycopy(this.a, 0, byArray, 0, this.size);
        return byArray;
    }

    @Override
    public boolean addAll(int n, ByteCollection byteCollection) {
        this.ensureIndex(n);
        int n2 = byteCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        ByteIterator byteIterator = byteCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = byteIterator.nextByte();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, ByteList byteList) {
        this.ensureIndex(n);
        int n2 = byteList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        byteList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(ByteCollection byteCollection) {
        int n;
        byte[] byArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (byteCollection.contains(byArray[n])) continue;
            byArray[n2++] = byArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        byte[] byArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(byArray[n])) continue;
            byArray[n2++] = byArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public ByteListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new ByteListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final ByteArrayList this$0;
            {
                this.this$0 = byteArrayList;
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
            public byte nextByte() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public byte previousByte() {
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
            public void add(byte by) {
                this.this$0.add(this.pos++, by);
                this.last = -1;
            }

            @Override
            public void set(byte by) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, by);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeByte(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public ByteArrayList clone() {
        ByteArrayList byteArrayList = new ByteArrayList(this.size);
        System.arraycopy(this.a, 0, byteArrayList.a, 0, this.size);
        byteArrayList.size = this.size;
        return byteArrayList;
    }

    public boolean equals(ByteArrayList byteArrayList) {
        if (byteArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != byteArrayList.size()) {
            return true;
        }
        byte[] byArray = this.a;
        byte[] byArray2 = byteArrayList.a;
        while (n-- != 0) {
            if (byArray[n] == byArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(ByteArrayList byteArrayList) {
        int n;
        int n2 = this.size();
        int n3 = byteArrayList.size();
        byte[] byArray = this.a;
        byte[] byArray2 = byteArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            byte by = byArray[n];
            byte by2 = byArray2[n];
            int n4 = Byte.compare(by, by2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeByte(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new byte[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readByte();
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

