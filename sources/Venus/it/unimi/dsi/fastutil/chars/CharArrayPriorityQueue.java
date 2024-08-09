/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharPriorityQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharArrayPriorityQueue
implements CharPriorityQueue,
Serializable {
    private static final long serialVersionUID = 1L;
    protected transient char[] array = CharArrays.EMPTY_ARRAY;
    protected int size;
    protected CharComparator c;
    protected transient int firstIndex;
    protected transient boolean firstIndexValid;

    public CharArrayPriorityQueue(int n, CharComparator charComparator) {
        if (n > 0) {
            this.array = new char[n];
        }
        this.c = charComparator;
    }

    public CharArrayPriorityQueue(int n) {
        this(n, null);
    }

    public CharArrayPriorityQueue(CharComparator charComparator) {
        this(0, charComparator);
    }

    public CharArrayPriorityQueue() {
        this(0, null);
    }

    public CharArrayPriorityQueue(char[] cArray, int n, CharComparator charComparator) {
        this(charComparator);
        this.array = cArray;
        this.size = n;
    }

    public CharArrayPriorityQueue(char[] cArray, CharComparator charComparator) {
        this(cArray, cArray.length, charComparator);
    }

    public CharArrayPriorityQueue(char[] cArray, int n) {
        this(cArray, n, null);
    }

    public CharArrayPriorityQueue(char[] cArray) {
        this(cArray, cArray.length);
    }

    private int findFirst() {
        if (this.firstIndexValid) {
            return this.firstIndex;
        }
        this.firstIndexValid = true;
        int n = this.size;
        int n2 = --n;
        char c = this.array[n2];
        if (this.c == null) {
            while (n-- != 0) {
                if (this.array[n] >= c) continue;
                n2 = n;
                c = this.array[n2];
            }
        } else {
            while (n-- != 0) {
                if (this.c.compare(this.array[n], c) >= 0) continue;
                n2 = n;
                c = this.array[n2];
            }
        }
        this.firstIndex = n2;
        return this.firstIndex;
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void enqueue(char c) {
        if (this.size == this.array.length) {
            this.array = CharArrays.grow(this.array, this.size + 1);
        }
        if (this.firstIndexValid) {
            if (this.c == null) {
                if (c < this.array[this.firstIndex]) {
                    this.firstIndex = this.size;
                }
            } else if (this.c.compare(c, this.array[this.firstIndex]) < 0) {
                this.firstIndex = this.size;
            }
        } else {
            this.firstIndexValid = false;
        }
        this.array[this.size++] = c;
    }

    @Override
    public char dequeueChar() {
        this.ensureNonEmpty();
        int n = this.findFirst();
        char c = this.array[n];
        System.arraycopy(this.array, n + 1, this.array, n, --this.size - n);
        this.firstIndexValid = false;
        return c;
    }

    @Override
    public char firstChar() {
        this.ensureNonEmpty();
        return this.array[this.findFirst()];
    }

    @Override
    public void changed() {
        this.ensureNonEmpty();
        this.firstIndexValid = false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.firstIndexValid = false;
    }

    public void trim() {
        this.array = CharArrays.trim(this.array, this.size);
    }

    @Override
    public CharComparator comparator() {
        return this.c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.array.length);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.array[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = new char[objectInputStream.readInt()];
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = objectInputStream.readChar();
        }
    }

    @Override
    public Comparator comparator() {
        return this.comparator();
    }
}

