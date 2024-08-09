/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteListIterator;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByteList
extends AbstractByteCollection
implements ByteList,
ByteStack {
    protected AbstractByteList() {
    }

    protected void ensureIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void ensureRestrictedIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    @Override
    public void add(int n, byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(byte by) {
        this.add(this.size(), by);
        return false;
    }

    @Override
    public byte removeByte(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte set(int n, byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Byte> collection) {
        this.ensureIndex(n);
        Iterator<? extends Byte> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (byte)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Byte> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public ByteListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public ByteListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ByteListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new ByteListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractByteList this$0;
            {
                this.this$0 = abstractByteList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size();
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
                return this.this$0.getByte(this.last);
            }

            @Override
            public byte previousByte() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getByte(this.pos);
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

    @Override
    public boolean contains(byte by) {
        return this.indexOf(by) >= 0;
    }

    @Override
    public int indexOf(byte by) {
        ByteListIterator byteListIterator = this.listIterator();
        while (byteListIterator.hasNext()) {
            byte by2 = byteListIterator.nextByte();
            if (by != by2) continue;
            return byteListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(byte by) {
        ByteListIterator byteListIterator = this.listIterator(this.size());
        while (byteListIterator.hasPrevious()) {
            byte by2 = byteListIterator.previousByte();
            if (by != by2) continue;
            return byteListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add((byte)1);
            }
        } else {
            while (n2-- != n) {
                this.removeByte(n2);
            }
        }
    }

    @Override
    public ByteList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ByteSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        ByteListIterator byteListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            byteListIterator.nextByte();
            byteListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, byte[] byArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > byArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + byArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, byArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, byte[] byArray) {
        this.addElements(n, byArray, 0, byArray.length);
    }

    @Override
    public void getElements(int n, byte[] byArray, int n2, int n3) {
        ByteListIterator byteListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > byArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + byArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            byArray[n2++] = byteListIterator.nextByte();
        }
    }

    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        ByteListIterator byteListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            byte by = byteListIterator.nextByte();
            n = 31 * n + by;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof List)) {
            return true;
        }
        List list = (List)object;
        int n = this.size();
        if (n != list.size()) {
            return true;
        }
        if (list instanceof ByteList) {
            ByteListIterator byteListIterator = this.listIterator();
            ByteListIterator byteListIterator2 = ((ByteList)list).listIterator();
            while (n-- != 0) {
                if (byteListIterator.nextByte() == byteListIterator2.nextByte()) continue;
                return true;
            }
            return false;
        }
        ByteListIterator byteListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(byteListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Byte> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof ByteList) {
            ByteListIterator byteListIterator = this.listIterator();
            ByteListIterator byteListIterator2 = ((ByteList)list).listIterator();
            while (byteListIterator.hasNext() && byteListIterator2.hasNext()) {
                byte by;
                byte by2 = byteListIterator.nextByte();
                int n = Byte.compare(by2, by = byteListIterator2.nextByte());
                if (n == 0) continue;
                return n;
            }
            return byteListIterator2.hasNext() ? -1 : (byteListIterator.hasNext() ? 1 : 0);
        }
        ByteListIterator byteListIterator = this.listIterator();
        ListIterator<? extends Byte> listIterator2 = list.listIterator();
        while (byteListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)byteListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (byteListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(byte by) {
        this.add(by);
    }

    @Override
    public byte popByte() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeByte(this.size() - 1);
    }

    @Override
    public byte topByte() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getByte(this.size() - 1);
    }

    @Override
    public byte peekByte(int n) {
        return this.getByte(this.size() - 1 - n);
    }

    @Override
    public boolean rem(byte by) {
        int n = this.indexOf(by);
        if (n == -1) {
            return true;
        }
        this.removeByte(n);
        return false;
    }

    @Override
    public boolean addAll(int n, ByteCollection byteCollection) {
        this.ensureIndex(n);
        ByteIterator byteIterator = byteCollection.iterator();
        boolean bl = byteIterator.hasNext();
        while (byteIterator.hasNext()) {
            this.add(n++, byteIterator.nextByte());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, ByteList byteList) {
        return this.addAll(n, (ByteCollection)byteList);
    }

    @Override
    public boolean addAll(ByteCollection byteCollection) {
        return this.addAll(this.size(), byteCollection);
    }

    @Override
    public boolean addAll(ByteList byteList) {
        return this.addAll(this.size(), byteList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ByteListIterator byteListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            byte by = byteListIterator.nextByte();
            stringBuilder.append(String.valueOf(by));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ByteIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ByteSubList
    extends AbstractByteList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractByteList.class.desiredAssertionStatus();

        public ByteSubList(ByteList byteList, int n, int n2) {
            this.l = byteList;
            this.from = n;
            this.to = n2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(byte by) {
            this.l.add(this.to, by);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, byte by) {
            this.ensureIndex(n);
            this.l.add(this.from + n, by);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Byte> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public byte getByte(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getByte(this.from + n);
        }

        @Override
        public byte removeByte(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeByte(this.from + n);
        }

        @Override
        public byte set(int n, byte by) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, by);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, byte[] byArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, byArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            this.l.removeElements(this.from + n, this.from + n2);
            this.to -= n2 - n;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(int n, byte[] byArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, byArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ByteListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new ByteListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractByteList.class.desiredAssertionStatus();
                final int val$index;
                final ByteSubList this$0;
                {
                    this.this$0 = byteSubList;
                    this.val$index = n;
                    this.pos = this.val$index;
                    this.last = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size();
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
                    return this.this$0.l.getByte(this.this$0.from + this.last);
                }

                @Override
                public byte previousByte() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getByte(this.this$0.from + this.pos);
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
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, by);
                    this.last = -1;
                    if (!$assertionsDisabled && !ByteSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
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
                    if (!$assertionsDisabled && !ByteSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ByteList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ByteSubList(this, n, n2);
        }

        @Override
        public boolean rem(byte by) {
            int n = this.indexOf(by);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeByte(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, ByteCollection byteCollection) {
            this.ensureIndex(n);
            return super.addAll(n, byteCollection);
        }

        @Override
        public boolean addAll(int n, ByteList byteList) {
            this.ensureIndex(n);
            return super.addAll(n, byteList);
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((List)object);
        }

        @Override
        public ByteIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(ByteSubList byteSubList) {
            return byteSubList.assertRange();
        }
    }
}

