/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
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
public class CharArraySet
extends AbstractCharSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient char[] a;
    private int size;

    public CharArraySet(char[] cArray) {
        this.a = cArray;
        this.size = cArray.length;
    }

    public CharArraySet() {
        this.a = CharArrays.EMPTY_ARRAY;
    }

    public CharArraySet(int n) {
        this.a = new char[n];
    }

    public CharArraySet(CharCollection charCollection) {
        this(charCollection.size());
        this.addAll(charCollection);
    }

    public CharArraySet(Collection<? extends Character> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public CharArraySet(char[] cArray, int n) {
        this.a = cArray;
        this.size = n;
        if (n > cArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + cArray.length + ")");
        }
    }

    private int findKey(char c) {
        int n = this.size;
        while (n-- != 0) {
            if (this.a[n] != c) continue;
            return n;
        }
        return 1;
    }

    @Override
    public CharIterator iterator() {
        return new CharIterator(this){
            int next;
            final CharArraySet this$0;
            {
                this.this$0 = charArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < CharArraySet.access$000(this.this$0);
            }

            @Override
            public char nextChar() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return CharArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = CharArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(CharArraySet.access$100(this.this$0), this.next + 1, CharArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(char c) {
        return this.findKey(c) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(char c) {
        int n = this.findKey(c);
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
    public boolean add(char c) {
        int n = this.findKey(c);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            char[] cArray = new char[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                cArray[n2] = this.a[n2];
            }
            this.a = cArray;
        }
        this.a[this.size++] = c;
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

    public CharArraySet clone() {
        CharArraySet charArraySet;
        try {
            charArraySet = (CharArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        charArraySet.a = (char[])this.a.clone();
        return charArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeChar(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new char[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readChar();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(CharArraySet charArraySet) {
        return charArraySet.size;
    }

    static char[] access$100(CharArraySet charArraySet) {
        return charArraySet.a;
    }

    static int access$010(CharArraySet charArraySet) {
        return charArraySet.size--;
    }
}

