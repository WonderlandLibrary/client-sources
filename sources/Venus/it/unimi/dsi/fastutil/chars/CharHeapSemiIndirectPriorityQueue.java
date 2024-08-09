/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIndirectPriorityQueue;
import it.unimi.dsi.fastutil.chars.CharSemiIndirectHeaps;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharHeapSemiIndirectPriorityQueue
implements CharIndirectPriorityQueue {
    protected final char[] refArray;
    protected int[] heap = IntArrays.EMPTY_ARRAY;
    protected int size;
    protected CharComparator c;

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int n, CharComparator charComparator) {
        if (n > 0) {
            this.heap = new int[n];
        }
        this.refArray = cArray;
        this.c = charComparator;
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int n) {
        this(cArray, n, null);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, CharComparator charComparator) {
        this(cArray, cArray.length, charComparator);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray) {
        this(cArray, cArray.length, null);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int[] nArray, int n, CharComparator charComparator) {
        this(cArray, 0, charComparator);
        this.heap = nArray;
        this.size = n;
        CharSemiIndirectHeaps.makeHeap(cArray, nArray, n, charComparator);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int[] nArray, CharComparator charComparator) {
        this(cArray, nArray, nArray.length, charComparator);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int[] nArray, int n) {
        this(cArray, nArray, n, null);
    }

    public CharHeapSemiIndirectPriorityQueue(char[] cArray, int[] nArray) {
        this(cArray, nArray, nArray.length);
    }

    protected void ensureElement(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.refArray.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
        }
    }

    @Override
    public void enqueue(int n) {
        this.ensureElement(n);
        if (this.size == this.heap.length) {
            this.heap = IntArrays.grow(this.heap, this.size + 1);
        }
        this.heap[this.size++] = n;
        CharSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
    }

    @Override
    public int dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int n = this.heap[0];
        this.heap[0] = this.heap[--this.size];
        if (this.size != 0) {
            CharSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
        }
        return n;
    }

    @Override
    public int first() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.heap[0];
    }

    @Override
    public void changed() {
        CharSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
    }

    @Override
    public void allChanged() {
        CharSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
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
        this.heap = IntArrays.trim(this.heap, this.size);
    }

    @Override
    public CharComparator comparator() {
        return this.c;
    }

    @Override
    public int front(int[] nArray) {
        return this.c == null ? CharSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray) : CharSemiIndirectHeaps.front(this.refArray, this.heap, this.size, nArray, this.c);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.refArray[this.heap[i]]);
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

