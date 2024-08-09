/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharBigList;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.chars.CharBigList;
import it.unimi.dsi.fastutil.chars.CharBigListIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CharBigArrayBigList
extends AbstractCharBigList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient char[][] a;
    protected long size;
    static final boolean $assertionsDisabled = !CharBigArrayBigList.class.desiredAssertionStatus();

    protected CharBigArrayBigList(char[][] cArray, boolean bl) {
        this.a = cArray;
    }

    public CharBigArrayBigList(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Initial capacity (" + l + ") is negative");
        }
        this.a = l == 0L ? CharBigArrays.EMPTY_BIG_ARRAY : CharBigArrays.newBigArray(l);
    }

    public CharBigArrayBigList() {
        this.a = CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
    }

    public CharBigArrayBigList(CharCollection charCollection) {
        this(charCollection.size());
        CharIterator charIterator = charCollection.iterator();
        while (charIterator.hasNext()) {
            this.add(charIterator.nextChar());
        }
    }

    public CharBigArrayBigList(CharBigList charBigList) {
        this(charBigList.size64());
        this.size = charBigList.size64();
        charBigList.getElements(0L, this.a, 0L, this.size);
    }

    public CharBigArrayBigList(char[][] cArray) {
        this(cArray, 0L, CharBigArrays.length(cArray));
    }

    public CharBigArrayBigList(char[][] cArray, long l, long l2) {
        this(l2);
        CharBigArrays.copy(cArray, l, this.a, 0L, l2);
        this.size = l2;
    }

    public CharBigArrayBigList(Iterator<? extends Character> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add(iterator2.next().charValue());
        }
    }

    public CharBigArrayBigList(CharIterator charIterator) {
        this();
        while (charIterator.hasNext()) {
            this.add(charIterator.nextChar());
        }
    }

    public char[][] elements() {
        return this.a;
    }

    public static CharBigArrayBigList wrap(char[][] cArray, long l) {
        if (l > CharBigArrays.length(cArray)) {
            throw new IllegalArgumentException("The specified length (" + l + ") is greater than the array size (" + CharBigArrays.length(cArray) + ")");
        }
        CharBigArrayBigList charBigArrayBigList = new CharBigArrayBigList(cArray, false);
        charBigArrayBigList.size = l;
        return charBigArrayBigList;
    }

    public static CharBigArrayBigList wrap(char[][] cArray) {
        return CharBigArrayBigList.wrap(cArray, CharBigArrays.length(cArray));
    }

    public void ensureCapacity(long l) {
        if (l <= (long)this.a.length || this.a == CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            return;
        }
        this.a = CharBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    private void grow(long l) {
        long l2 = CharBigArrays.length(this.a);
        if (l <= l2) {
            return;
        }
        if (this.a != CharBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
            l = Math.max(l2 + (l2 >> 1), l);
        } else if (l < 10L) {
            l = 10L;
        }
        this.a = CharBigArrays.forceCapacity(this.a, l, this.size);
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(long l, char c) {
        this.ensureIndex(l);
        this.grow(this.size + 1L);
        if (l != this.size) {
            CharBigArrays.copy(this.a, l, this.a, l + 1L, this.size - l);
        }
        CharBigArrays.set(this.a, l, c);
        ++this.size;
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(char c) {
        this.grow(this.size + 1L);
        CharBigArrays.set(this.a, this.size++, c);
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public char getChar(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        return CharBigArrays.get(this.a, l);
    }

    @Override
    public long indexOf(char c) {
        for (long i = 0L; i < this.size; ++i) {
            if (c != CharBigArrays.get(this.a, i)) continue;
            return i;
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(char c) {
        long l = this.size;
        while (l-- != 0L) {
            if (c != CharBigArrays.get(this.a, l)) continue;
            return l;
        }
        return -1L;
    }

    @Override
    public char removeChar(long l) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        char c = CharBigArrays.get(this.a, l);
        --this.size;
        if (l != this.size) {
            CharBigArrays.copy(this.a, l + 1L, this.a, l, this.size - l);
        }
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return c;
    }

    @Override
    public boolean rem(char c) {
        long l = this.indexOf(c);
        if (l == -1L) {
            return true;
        }
        this.removeChar(l);
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public char set(long l, char c) {
        if (l >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size + ")");
        }
        char c2 = CharBigArrays.get(this.a, l);
        CharBigArrays.set(this.a, l, c);
        return c2;
    }

    @Override
    public boolean removeAll(CharCollection charCollection) {
        long l;
        char[] cArray = null;
        char[] cArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                cArray = this.a[++n];
            }
            if (!charCollection.contains((char)cArray[n2])) {
                if (n4 == 0x8000000) {
                    cArray2 = this.a[++n3];
                    n4 = 0;
                }
                cArray2[n4++] = cArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        long l;
        char[] cArray = null;
        char[] cArray2 = null;
        int n = -1;
        int n2 = 0x8000000;
        int n3 = -1;
        int n4 = 0x8000000;
        for (l = 0L; l < this.size; ++l) {
            if (n2 == 0x8000000) {
                n2 = 0;
                cArray = this.a[++n];
            }
            if (!collection.contains(Character.valueOf((char)cArray[n2]))) {
                if (n4 == 0x8000000) {
                    cArray2 = this.a[++n3];
                    n4 = 0;
                }
                cArray2[n4++] = cArray[n2];
            }
            ++n2;
        }
        l = BigArrays.index(n3, n4);
        boolean bl = this.size != l;
        this.size = l;
        return bl;
    }

    @Override
    public void clear() {
        this.size = 0L;
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public void size(long l) {
        if (l > CharBigArrays.length(this.a)) {
            this.ensureCapacity(l);
        }
        if (l > this.size) {
            CharBigArrays.fill(this.a, this.size, l, '\u0000');
        }
        this.size = l;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public void trim() {
        this.trim(0L);
    }

    public void trim(long l) {
        long l2 = CharBigArrays.length(this.a);
        if (l >= l2 || this.size == l2) {
            return;
        }
        this.a = CharBigArrays.trim(this.a, Math.max(l, this.size));
        if (!$assertionsDisabled && this.size > CharBigArrays.length(this.a)) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(long l, char[][] cArray, long l2, long l3) {
        CharBigArrays.copy(this.a, l, cArray, l2, l3);
    }

    @Override
    public void removeElements(long l, long l2) {
        BigArrays.ensureFromTo(this.size, l, l2);
        CharBigArrays.copy(this.a, l2, this.a, l, this.size - l2);
        this.size -= l2 - l;
    }

    @Override
    public void addElements(long l, char[][] cArray, long l2, long l3) {
        this.ensureIndex(l);
        CharBigArrays.ensureOffsetLength(cArray, l2, l3);
        this.grow(this.size + l3);
        CharBigArrays.copy(this.a, l, this.a, l + l3, this.size - l);
        CharBigArrays.copy(cArray, l2, this.a, l, l3);
        this.size += l3;
    }

    @Override
    public CharBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new CharBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final CharBigArrayBigList this$0;
            {
                this.this$0 = charBigArrayBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public char nextChar() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return CharBigArrays.get(this.this$0.a, this.last);
            }

            @Override
            public char previousChar() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return CharBigArrays.get(this.this$0.a, this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(char c) {
                this.this$0.add(this.pos++, c);
                this.last = -1L;
            }

            @Override
            public void set(char c) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, c);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeChar(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    public CharBigArrayBigList clone() {
        CharBigArrayBigList charBigArrayBigList = new CharBigArrayBigList(this.size);
        CharBigArrays.copy(this.a, 0L, charBigArrayBigList.a, 0L, this.size);
        charBigArrayBigList.size = this.size;
        return charBigArrayBigList;
    }

    public boolean equals(CharBigArrayBigList charBigArrayBigList) {
        if (charBigArrayBigList == this) {
            return false;
        }
        long l = this.size64();
        if (l != charBigArrayBigList.size64()) {
            return true;
        }
        char[][] cArray = this.a;
        char[][] cArray2 = charBigArrayBigList.a;
        while (l-- != 0L) {
            if (CharBigArrays.get(cArray, l) == CharBigArrays.get(cArray2, l)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(CharBigArrayBigList charBigArrayBigList) {
        long l = this.size64();
        long l2 = charBigArrayBigList.size64();
        char[][] cArray = this.a;
        char[][] cArray2 = charBigArrayBigList.a;
        int n = 0;
        while ((long)n < l && (long)n < l2) {
            char c;
            char c2 = CharBigArrays.get(cArray, n);
            int n2 = Character.compare(c2, c = CharBigArrays.get(cArray2, n));
            if (n2 != 0) {
                return n2;
            }
            ++n;
        }
        return (long)n < l2 ? -1 : ((long)n < l ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = 0;
        while ((long)n < this.size) {
            objectOutputStream.writeChar(CharBigArrays.get(this.a, n));
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = CharBigArrays.newBigArray(this.size);
        int n = 0;
        while ((long)n < this.size) {
            CharBigArrays.set(this.a, n, objectInputStream.readChar());
            ++n;
        }
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

