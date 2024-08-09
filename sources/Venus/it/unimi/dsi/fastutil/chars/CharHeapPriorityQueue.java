/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharHeaps;
import it.unimi.dsi.fastutil.chars.CharPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharHeapPriorityQueue
implements CharPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient char[] heap = CharArrays.EMPTY_ARRAY;
    protected int size;
    protected CharComparator c;

    public CharHeapPriorityQueue(int n, CharComparator charComparator) {
        if (n > 0) {
            this.heap = new char[n];
        }
        this.c = charComparator;
    }

    public CharHeapPriorityQueue(int n) {
        this(n, null);
    }

    public CharHeapPriorityQueue(CharComparator charComparator) {
        this(0, charComparator);
    }

    public CharHeapPriorityQueue() {
        this(0, null);
    }

    public CharHeapPriorityQueue(char[] cArray, int n, CharComparator charComparator) {
        this(charComparator);
        this.heap = cArray;
        this.size = n;
        CharHeaps.makeHeap(cArray, n, charComparator);
    }

    public CharHeapPriorityQueue(char[] cArray, CharComparator charComparator) {
        this(cArray, cArray.length, charComparator);
    }

    public CharHeapPriorityQueue(char[] cArray, int n) {
        this(cArray, n, null);
    }

    public CharHeapPriorityQueue(char[] cArray) {
        this(cArray, cArray.length);
    }

    public CharHeapPriorityQueue(CharCollection charCollection, CharComparator charComparator) {
        this(charCollection.toCharArray(), charComparator);
    }

    public CharHeapPriorityQueue(CharCollection charCollection) {
        this(charCollection, (CharComparator)null);
    }

    public CharHeapPriorityQueue(Collection<? extends Character> collection, CharComparator charComparator) {
        this(collection.size(), charComparator);
        Iterator<? extends Character> iterator2 = collection.iterator();
        int n = collection.size();
        for (int i = 0; i < n; ++i) {
            this.heap[i] = iterator2.next().charValue();
        }
    }

    public CharHeapPriorityQueue(Collection<? extends Character> collection) {
        this(collection, null);
    }

    @Override
    public void enqueue(char c) {
        if (this.size == this.heap.length) {
            this.heap = CharArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = c;
        CharHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public char dequeueChar() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        char c = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            CharHeaps.downHeap(this.heap, this.size, 0, this.c);
        }
        return c;
    }

    @Override
    public char firstChar() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        CharHeaps.downHeap(this.heap, this.size, 0, this.c);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    public void trim() {
        this.heap = CharArrays.trim(this.heap, this.size);
    }

    @Override
    public CharComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.heap.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.heap[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.heap = new char[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.heap[i] = objectInputStream.readChar();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

