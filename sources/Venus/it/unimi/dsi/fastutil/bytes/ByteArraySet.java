/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteSet;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteArraySet
extends AbstractByteSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient byte[] a;
    private int size;

    public ByteArraySet(byte[] byArray) {
        this.a = byArray;
        this.size = byArray.length;
    }

    public ByteArraySet() {
        this.a = ByteArrays.EMPTY_ARRAY;
    }

    public ByteArraySet(int n) {
        this.a = new byte[n];
    }

    public ByteArraySet(ByteCollection byteCollection) {
        this(byteCollection.size());
        this.addAll(byteCollection);
    }

    public ByteArraySet(Collection<? extends Byte> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public ByteArraySet(byte[] byArray, int n) {
        this.a = byArray;
        this.size = n;
        if (n > byArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + byArray.length + ")");
        }
    }

    private int findKey(byte by) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != by) continue;
            return n;
        }
        return 1;
    }

    @Override
    public ByteIterator iterator() {
        return new ByteIterator(this){
            int next;
            final ByteArraySet this$0;
            {
                this.this$0 = byteArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < ByteArraySet.access$000(this.this$0);
            }

            @Override
            public byte nextByte() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return ByteArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = ByteArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(ByteArraySet.access$100(this.this$0), this.next + 1, ByteArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(byte by) {
        return this.findKey(by) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(byte by) {
        int n = this.findKey(by);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        return false;
    }

    @Override
    public boolean add(byte by) {
        int n = this.findKey(by);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            byte[] byArray = new byte[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                byArray[n2] = this.a[n2];
            }
            this.a = byArray;
        }
        this.a[this.size++] = by;
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public ByteArraySet clone() {
        ByteArraySet byteArraySet;
        try {
            byteArraySet = (ByteArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        byteArraySet.a = (byte[])this.a.clone();
        return byteArraySet;
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
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(ByteArraySet byteArraySet) {
        return byteArraySet.size;
    }

    static byte[] access$100(ByteArraySet byteArraySet) {
        return byteArraySet.a;
    }

    static int access$010(ByteArraySet byteArraySet) {
        return byteArraySet.size--;
    }
}

