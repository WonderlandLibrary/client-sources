/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.chars.AbstractCharList;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharListIterator;
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
public class CharArrayList
extends AbstractCharList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient char[] a;
    protected int size;
    static final boolean $assertionsDisabled = !CharArrayList.class.desiredAssertionStatus();

    protected CharArrayList(char[] cArray, boolean bl) {
        this.a = cArray;
    }

    public CharArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? CharArrays.EMPTY_ARRAY : new char[n];
    }

    public CharArrayList() {
        this.a = CharArrays.DEFAULT_EMPTY_ARRAY;
    }

    public CharArrayList(Collection<? extends Character> collection) {
        this(collection.size());
        this.size = CharIterators.unwrap(CharIterators.asCharIterator(collection.iterator()), this.a);
    }

    public CharArrayList(CharCollection charCollection) {
        this(charCollection.size());
        this.size = CharIterators.unwrap(charCollection.iterator(), this.a);
    }

    public CharArrayList(CharList charList) {
        this(charList.size());
        this.size = charList.size();
        charList.getElements(0, this.a, 0, this.size);
    }

    public CharArrayList(char[] cArray) {
        this(cArray, 0, cArray.length);
    }

    public CharArrayList(char[] cArray, int n, int n2) {
        this(n2);
        System.arraycopy(cArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public CharArrayList(Iterator<? extends Character> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next().charValue());
        }
    }

    public CharArrayList(CharIterator charIterator) {
        this();
        while (charIterator.hasNext()) {
            this.add(charIterator.nextChar());
        }
    }

    public char[] elements() {
        return this.a;
    }

    public static CharArrayList wrap(char[] cArray, int n) {
        if (n > cArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + cArray.length + ")");
        }
        CharArrayList charArrayList = new CharArrayList(cArray, false);
        charArrayList.size = n;
        return charArrayList;
    }

    public static CharArrayList wrap(char[] cArray) {
        return CharArrayList.wrap(cArray, cArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == CharArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = CharArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != CharArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = CharArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, char c) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = c;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(char c) {
        this.grow(this.size + 1);
        this.a[this.size++] = c;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public char getChar(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(char c) {
        for (int i = 0; i < this.size; ++i) {
            if (c != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(char c) {
        int n = this.size;
        while (n-- != 0) {
            if (c != this.a[n]) continue;
            return n;
        }
        return 1;
    }

    @Override
    public char removeChar(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        char c = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return c;
    }

    @Override
    public boolean rem(char c) {
        int n = this.indexOf(c);
        if (n == -1) {
            return true;
        }
        this.removeChar(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public char set(int n, char c) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        char c2 = this.a[n];
        this.a[n] = c;
        return c2;
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
            java.util.Arrays.fill(this.a, this.size, n, '\u0000');
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
        char[] cArray = new char[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, cArray, 0, this.size);
        this.a = cArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, char[] cArray, int n2, int n3) {
        CharArrays.ensureOffsetLength(cArray, n2, n3);
        System.arraycopy(this.a, n, cArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, char[] cArray, int n2, int n3) {
        this.ensureIndex(n);
        CharArrays.ensureOffsetLength(cArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(cArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public char[] toArray(char[] cArray) {
        if (cArray == null || cArray.length < this.size) {
            cArray = new char[this.size];
        }
        System.arraycopy(this.a, 0, cArray, 0, this.size);
        return cArray;
    }

    @Override
    public boolean addAll(int n, CharCollection charCollection) {
        this.ensureIndex(n);
        int n2 = charCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        CharIterator charIterator = charCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = charIterator.nextChar();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, CharList charList) {
        this.ensureIndex(n);
        int n2 = charList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        charList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(CharCollection charCollection) {
        int n;
        char[] cArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (charCollection.contains(cArray[n])) continue;
            cArray[n2++] = cArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        char[] cArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(Character.valueOf(cArray[n]))) continue;
            cArray[n2++] = cArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public CharListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new CharListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final CharArrayList this$0;
            {
                this.this$0 = charArrayList;
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
            public char nextChar() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public char previousChar() {
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
            public void add(char c) {
                this.this$0.add(this.pos++, c);
                this.last = -1;
            }

            @Override
            public void set(char c) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, c);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeChar(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public CharArrayList clone() {
        CharArrayList charArrayList = new CharArrayList(this.size);
        System.arraycopy(this.a, 0, charArrayList.a, 0, this.size);
        charArrayList.size = this.size;
        return charArrayList;
    }

    public boolean equals(CharArrayList charArrayList) {
        if (charArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != charArrayList.size()) {
            return true;
        }
        char[] cArray = this.a;
        char[] cArray2 = charArrayList.a;
        while (n-- != 0) {
            if (cArray[n] == cArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(CharArrayList charArrayList) {
        int n;
        int n2 = this.size();
        int n3 = charArrayList.size();
        char[] cArray = this.a;
        char[] cArray2 = charArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            char c = cArray[n];
            char c2 = cArray2[n];
            int n4 = Character.compare(c, c2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
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
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

