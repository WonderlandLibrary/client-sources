/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
public class BooleanArrayList
extends AbstractBooleanList
implements RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient boolean[] a;
    protected int size;
    static final boolean $assertionsDisabled = !BooleanArrayList.class.desiredAssertionStatus();

    protected BooleanArrayList(boolean[] blArray, boolean bl) {
        this.a = blArray;
    }

    public BooleanArrayList(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        this.a = n == 0 ? BooleanArrays.EMPTY_ARRAY : new boolean[n];
    }

    public BooleanArrayList() {
        this.a = BooleanArrays.DEFAULT_EMPTY_ARRAY;
    }

    public BooleanArrayList(Collection<? extends Boolean> collection) {
        this(collection.size());
        this.size = BooleanIterators.unwrap(BooleanIterators.asBooleanIterator(collection.iterator()), this.a);
    }

    public BooleanArrayList(BooleanCollection booleanCollection) {
        this(booleanCollection.size());
        this.size = BooleanIterators.unwrap(booleanCollection.iterator(), this.a);
    }

    public BooleanArrayList(BooleanList booleanList) {
        this(booleanList.size());
        this.size = booleanList.size();
        booleanList.getElements(0, this.a, 0, this.size);
    }

    public BooleanArrayList(boolean[] blArray) {
        this(blArray, 0, blArray.length);
    }

    public BooleanArrayList(boolean[] blArray, int n, int n2) {
        this(n2);
        System.arraycopy(blArray, n, this.a, 0, n2);
        this.size = n2;
    }

    public BooleanArrayList(Iterator<? extends Boolean> iterator2) {
        this();
        while (iterator2.hasNext()) {
            this.add((boolean)iterator2.next());
        }
    }

    public BooleanArrayList(BooleanIterator booleanIterator) {
        this();
        while (booleanIterator.hasNext()) {
            this.add(booleanIterator.nextBoolean());
        }
    }

    public boolean[] elements() {
        return this.a;
    }

    public static BooleanArrayList wrap(boolean[] blArray, int n) {
        if (n > blArray.length) {
            throw new IllegalArgumentException("The specified length (" + n + ") is greater than the array size (" + blArray.length + ")");
        }
        BooleanArrayList booleanArrayList = new BooleanArrayList(blArray, false);
        booleanArrayList.size = n;
        return booleanArrayList;
    }

    public static BooleanArrayList wrap(boolean[] blArray) {
        return BooleanArrayList.wrap(blArray, blArray.length);
    }

    public void ensureCapacity(int n) {
        if (n <= this.a.length || this.a == BooleanArrays.DEFAULT_EMPTY_ARRAY) {
            return;
        }
        this.a = BooleanArrays.ensureCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    private void grow(int n) {
        if (n <= this.a.length) {
            return;
        }
        if (this.a != BooleanArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min((long)this.a.length + (long)(this.a.length >> 1), 0x7FFFFFF7L), (long)n);
        } else if (n < 10) {
            n = 10;
        }
        this.a = BooleanArrays.forceCapacity(this.a, n, this.size);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void add(int n, boolean bl) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = bl;
        ++this.size;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean add(boolean bl) {
        this.grow(this.size + 1);
        this.a[this.size++] = bl;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean getBoolean(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(boolean bl) {
        for (int i = 0; i < this.size; ++i) {
            if (bl != this.a[i]) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(boolean bl) {
        int n = this.size;
        while (n-- != 0) {
            if (bl != this.a[n]) continue;
            return n;
        }
        return 1;
    }

    @Override
    public boolean removeBoolean(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        boolean bl = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return bl;
    }

    @Override
    public boolean rem(boolean bl) {
        int n = this.indexOf(bl);
        if (n == -1) {
            return true;
        }
        this.removeBoolean(n);
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean set(int n, boolean bl) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        boolean bl2 = this.a[n];
        this.a[n] = bl;
        return bl2;
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
            java.util.Arrays.fill(this.a, this.size, n, false);
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
        boolean[] blArray = new boolean[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, blArray, 0, this.size);
        this.a = blArray;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
    }

    @Override
    public void getElements(int n, boolean[] blArray, int n2, int n3) {
        BooleanArrays.ensureOffsetLength(blArray, n2, n3);
        System.arraycopy(this.a, n, blArray, n2, n3);
    }

    @Override
    public void removeElements(int n, int n2) {
        Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }

    @Override
    public void addElements(int n, boolean[] blArray, int n2, int n3) {
        this.ensureIndex(n);
        BooleanArrays.ensureOffsetLength(blArray, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(blArray, n2, this.a, n, n3);
        this.size += n3;
    }

    @Override
    public boolean[] toArray(boolean[] blArray) {
        if (blArray == null || blArray.length < this.size) {
            blArray = new boolean[this.size];
        }
        System.arraycopy(this.a, 0, blArray, 0, this.size);
        return blArray;
    }

    @Override
    public boolean addAll(int n, BooleanCollection booleanCollection) {
        this.ensureIndex(n);
        int n2 = booleanCollection.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        BooleanIterator booleanIterator = booleanCollection.iterator();
        this.size += n2;
        while (n2-- != 0) {
            this.a[n++] = booleanIterator.nextBoolean();
        }
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean addAll(int n, BooleanList booleanList) {
        this.ensureIndex(n);
        int n2 = booleanList.size();
        if (n2 == 0) {
            return true;
        }
        this.grow(this.size + n2);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + n2, this.size - n);
        }
        booleanList.getElements(0, this.a, n, n2);
        this.size += n2;
        if (!$assertionsDisabled && this.size > this.a.length) {
            throw new AssertionError();
        }
        return false;
    }

    @Override
    public boolean removeAll(BooleanCollection booleanCollection) {
        int n;
        boolean[] blArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (booleanCollection.contains(blArray[n])) continue;
            blArray[n2++] = blArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        int n;
        boolean[] blArray = this.a;
        int n2 = 0;
        for (n = 0; n < this.size; ++n) {
            if (collection.contains(blArray[n])) continue;
            blArray[n2++] = blArray[n];
        }
        n = this.size != n2 ? 1 : 0;
        this.size = n2;
        return n != 0;
    }

    @Override
    public BooleanListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new BooleanListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final BooleanArrayList this$0;
            {
                this.this$0 = booleanArrayList;
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
            public boolean nextBoolean() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.a[this.last];
            }

            @Override
            public boolean previousBoolean() {
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
            public void add(boolean bl) {
                this.this$0.add(this.pos++, bl);
                this.last = -1;
            }

            @Override
            public void set(boolean bl) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, bl);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeBoolean(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    public BooleanArrayList clone() {
        BooleanArrayList booleanArrayList = new BooleanArrayList(this.size);
        System.arraycopy(this.a, 0, booleanArrayList.a, 0, this.size);
        booleanArrayList.size = this.size;
        return booleanArrayList;
    }

    public boolean equals(BooleanArrayList booleanArrayList) {
        if (booleanArrayList == this) {
            return false;
        }
        int n = this.size();
        if (n != booleanArrayList.size()) {
            return true;
        }
        boolean[] blArray = this.a;
        boolean[] blArray2 = booleanArrayList.a;
        while (n-- != 0) {
            if (blArray[n] == blArray2[n]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BooleanArrayList booleanArrayList) {
        int n;
        int n2 = this.size();
        int n3 = booleanArrayList.size();
        boolean[] blArray = this.a;
        boolean[] blArray2 = booleanArrayList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            boolean bl = blArray[n];
            boolean bl2 = blArray2[n];
            int n4 = Boolean.compare(bl, bl2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeBoolean(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new boolean[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readBoolean();
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

