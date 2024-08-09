/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.ints.IntBigList;
import it.unimi.dsi.fastutil.ints.IntBigListIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractIntBigList
extends AbstractIntCollection
implements IntBigList,
IntStack {
    protected AbstractIntBigList() {
    }

    protected void ensureIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l > this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than list size (" + this.size64() + ")");
        }
    }

    protected void ensureRestrictedIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l >= this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size64() + ")");
        }
    }

    @Override
    public void add(long l, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(int n) {
        this.add(this.size64(), n);
        return false;
    }

    @Override
    public int removeInt(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int set(long l, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Integer> collection) {
        this.ensureIndex(l);
        Iterator<? extends Integer> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public IntBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public IntBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public IntBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new IntBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractIntBigList this$0;
            {
                this.this$0 = abstractIntBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getInt(this.last);
            }

            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getInt(this.pos);
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
            public void add(int n) {
                this.this$0.add(this.pos++, n);
                this.last = -1L;
            }

            @Override
            public void set(int n) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(int n) {
        return this.indexOf(n) >= 0L;
    }

    @Override
    public long indexOf(int n) {
        IntBigListIterator intBigListIterator = this.listIterator();
        while (intBigListIterator.hasNext()) {
            int n2 = intBigListIterator.nextInt();
            if (n != n2) continue;
            return intBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(int n) {
        IntBigListIterator intBigListIterator = this.listIterator(this.size64());
        while (intBigListIterator.hasPrevious()) {
            int n2 = intBigListIterator.previousInt();
            if (n != n2) continue;
            return intBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add(1);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public IntBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new IntSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        IntBigListIterator intBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            intBigListIterator.nextInt();
            intBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, int[][] nArray, long l2, long l3) {
        this.ensureIndex(l);
        IntBigArrays.ensureOffsetLength(nArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, IntBigArrays.get(nArray, l2++));
        }
    }

    @Override
    public void addElements(long l, int[][] nArray) {
        this.addElements(l, nArray, 0L, IntBigArrays.length(nArray));
    }

    @Override
    public void getElements(long l, int[][] nArray, long l2, long l3) {
        IntBigListIterator intBigListIterator = this.listIterator(l);
        IntBigArrays.ensureOffsetLength(nArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            IntBigArrays.set(nArray, l2++, intBigListIterator.nextInt());
        }
    }

    @Override
    public void clear() {
        this.removeElements(0L, this.size64());
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        IntBigListIterator intBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            int n2 = intBigListIterator.nextInt();
            n = 31 * n + n2;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof BigList)) {
            return true;
        }
        BigList bigList = (BigList)object;
        long l = this.size64();
        if (l != bigList.size64()) {
            return true;
        }
        if (bigList instanceof IntBigList) {
            IntBigListIterator intBigListIterator = this.listIterator();
            IntBigListIterator intBigListIterator2 = ((IntBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (intBigListIterator.nextInt() == intBigListIterator2.nextInt()) continue;
                return true;
            }
            return false;
        }
        IntBigListIterator intBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(intBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Integer> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof IntBigList) {
            IntBigListIterator intBigListIterator = this.listIterator();
            IntBigListIterator intBigListIterator2 = ((IntBigList)bigList).listIterator();
            while (intBigListIterator.hasNext() && intBigListIterator2.hasNext()) {
                int n;
                int n2 = intBigListIterator.nextInt();
                int n3 = Integer.compare(n2, n = intBigListIterator2.nextInt());
                if (n3 == 0) continue;
                return n3;
            }
            return intBigListIterator2.hasNext() ? -1 : (intBigListIterator.hasNext() ? 1 : 0);
        }
        IntBigListIterator intBigListIterator = this.listIterator();
        BigListIterator<? extends Integer> bigListIterator = bigList.listIterator();
        while (intBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)intBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (intBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(int n) {
        this.add(n);
    }

    @Override
    public int popInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeInt(this.size64() - 1L);
    }

    @Override
    public int topInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getInt(this.size64() - 1L);
    }

    @Override
    public int peekInt(int n) {
        return this.getInt(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(int n) {
        long l = this.indexOf(n);
        if (l == -1L) {
            return true;
        }
        this.removeInt(l);
        return false;
    }

    @Override
    public boolean addAll(long l, IntCollection intCollection) {
        return this.addAll(l, (Collection<? extends Integer>)intCollection);
    }

    @Override
    public boolean addAll(long l, IntBigList intBigList) {
        return this.addAll(l, (IntCollection)intBigList);
    }

    @Override
    public boolean addAll(IntCollection intCollection) {
        return this.addAll(this.size64(), intCollection);
    }

    @Override
    public boolean addAll(IntBigList intBigList) {
        return this.addAll(this.size64(), intBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Integer n) {
        this.add(l, (int)n);
    }

    @Override
    @Deprecated
    public Integer set(long l, Integer n) {
        return this.set(l, (int)n);
    }

    @Override
    @Deprecated
    public Integer get(long l) {
        return this.getInt(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Integer)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Integer)object);
    }

    @Override
    @Deprecated
    public Integer remove(long l) {
        return this.removeInt(l);
    }

    @Override
    @Deprecated
    public void push(Integer n) {
        this.push((int)n);
    }

    @Override
    @Deprecated
    public Integer pop() {
        return this.popInt();
    }

    @Override
    @Deprecated
    public Integer top() {
        return this.topInt();
    }

    @Override
    @Deprecated
    public Integer peek(int n) {
        return this.peekInt(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        IntBigListIterator intBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            int n = intBigListIterator.nextInt();
            stringBuilder.append(String.valueOf(n));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public IntIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public void add(long l, Object object) {
        this.add(l, (Integer)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Integer)object);
    }

    @Override
    @Deprecated
    public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    public Object get(long l) {
        return this.get(l);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((BigList)object);
    }

    @Override
    @Deprecated
    public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    public void push(Object object) {
        this.push((Integer)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IntSubList
    extends AbstractIntBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractIntBigList.class.desiredAssertionStatus();

        public IntSubList(IntBigList intBigList, long l, long l2) {
            this.l = intBigList;
            this.from = l;
            this.to = l2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(int n) {
            this.l.add(this.to, n);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, int n) {
            this.ensureIndex(l);
            this.l.add(this.from + l, n);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Integer> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public int getInt(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getInt(this.from + l);
        }

        @Override
        public int removeInt(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeInt(this.from + l);
        }

        @Override
        public int set(long l, int n) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, n);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, int[][] nArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, nArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            this.l.removeElements(this.from + l, this.from + l2);
            this.to -= l2 - l;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(long l, int[][] nArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, nArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public IntBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new IntBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractIntBigList.class.desiredAssertionStatus();
                final long val$index;
                final IntSubList this$0;
                {
                    this.this$0 = intSubList;
                    this.val$index = l;
                    this.pos = this.val$index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getInt(this.this$0.from + this.last);
                }

                @Override
                public int previousInt() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getInt(this.this$0.from + this.pos);
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
                public void add(int n) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, n);
                    this.last = -1L;
                    if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(int n) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, n);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeInt(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public IntBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new IntSubList(this, l, l2);
        }

        @Override
        public boolean rem(int n) {
            long l = this.indexOf(n);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeInt(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, IntCollection intCollection) {
            this.ensureIndex(l);
            return super.addAll(l, intCollection);
        }

        @Override
        public boolean addAll(long l, IntBigList intBigList) {
            this.ensureIndex(l);
            return super.addAll(l, intBigList);
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            super.add(l, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Integer)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return super.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return super.get(l);
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public IntIterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((BigList)object);
        }

        @Override
        @Deprecated
        public Object peek(int n) {
            return super.peek(n);
        }

        @Override
        @Deprecated
        public Object top() {
            return super.top();
        }

        @Override
        @Deprecated
        public Object pop() {
            return super.pop();
        }

        @Override
        @Deprecated
        public void push(Object object) {
            super.push((Integer)object);
        }

        static boolean access$000(IntSubList intSubList) {
            return intSubList.assertRange();
        }
    }
}

