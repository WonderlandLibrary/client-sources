/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortBigList;
import it.unimi.dsi.fastutil.shorts.ShortBigListIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShortBigList
extends AbstractShortCollection
implements ShortBigList,
ShortStack {
    protected AbstractShortBigList() {
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
    public void add(long l, short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(short s) {
        this.add(this.size64(), s);
        return false;
    }

    @Override
    public short removeShort(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short set(long l, short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Short> collection) {
        this.ensureIndex(l);
        Iterator<? extends Short> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Short> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public ShortBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public ShortBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public ShortBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new ShortBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractShortBigList this$0;
            {
                this.this$0 = abstractShortBigList;
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
            public short nextShort() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getShort(this.last);
            }

            @Override
            public short previousShort() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getShort(this.pos);
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
            public void add(short s) {
                this.this$0.add(this.pos++, s);
                this.last = -1L;
            }

            @Override
            public void set(short s) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, s);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeShort(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(short s) {
        return this.indexOf(s) >= 0L;
    }

    @Override
    public long indexOf(short s) {
        ShortBigListIterator shortBigListIterator = this.listIterator();
        while (shortBigListIterator.hasNext()) {
            short s2 = shortBigListIterator.nextShort();
            if (s != s2) continue;
            return shortBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(short s) {
        ShortBigListIterator shortBigListIterator = this.listIterator(this.size64());
        while (shortBigListIterator.hasPrevious()) {
            short s2 = shortBigListIterator.previousShort();
            if (s != s2) continue;
            return shortBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add((short)1);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public ShortBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new ShortSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        ShortBigListIterator shortBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            shortBigListIterator.nextShort();
            shortBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, short[][] sArray, long l2, long l3) {
        this.ensureIndex(l);
        ShortBigArrays.ensureOffsetLength(sArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, ShortBigArrays.get(sArray, l2++));
        }
    }

    @Override
    public void addElements(long l, short[][] sArray) {
        this.addElements(l, sArray, 0L, ShortBigArrays.length(sArray));
    }

    @Override
    public void getElements(long l, short[][] sArray, long l2, long l3) {
        ShortBigListIterator shortBigListIterator = this.listIterator(l);
        ShortBigArrays.ensureOffsetLength(sArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            ShortBigArrays.set(sArray, l2++, shortBigListIterator.nextShort());
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
        ShortBigListIterator shortBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            short s = shortBigListIterator.nextShort();
            n = 31 * n + s;
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
        if (bigList instanceof ShortBigList) {
            ShortBigListIterator shortBigListIterator = this.listIterator();
            ShortBigListIterator shortBigListIterator2 = ((ShortBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (shortBigListIterator.nextShort() == shortBigListIterator2.nextShort()) continue;
                return true;
            }
            return false;
        }
        ShortBigListIterator shortBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(shortBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Short> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof ShortBigList) {
            ShortBigListIterator shortBigListIterator = this.listIterator();
            ShortBigListIterator shortBigListIterator2 = ((ShortBigList)bigList).listIterator();
            while (shortBigListIterator.hasNext() && shortBigListIterator2.hasNext()) {
                short s;
                short s2 = shortBigListIterator.nextShort();
                int n = Short.compare(s2, s = shortBigListIterator2.nextShort());
                if (n == 0) continue;
                return n;
            }
            return shortBigListIterator2.hasNext() ? -1 : (shortBigListIterator.hasNext() ? 1 : 0);
        }
        ShortBigListIterator shortBigListIterator = this.listIterator();
        BigListIterator<? extends Short> bigListIterator = bigList.listIterator();
        while (shortBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)shortBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (shortBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(short s) {
        this.add(s);
    }

    @Override
    public short popShort() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeShort(this.size64() - 1L);
    }

    @Override
    public short topShort() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getShort(this.size64() - 1L);
    }

    @Override
    public short peekShort(int n) {
        return this.getShort(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(short s) {
        long l = this.indexOf(s);
        if (l == -1L) {
            return true;
        }
        this.removeShort(l);
        return false;
    }

    @Override
    public boolean addAll(long l, ShortCollection shortCollection) {
        return this.addAll(l, (Collection<? extends Short>)shortCollection);
    }

    @Override
    public boolean addAll(long l, ShortBigList shortBigList) {
        return this.addAll(l, (ShortCollection)shortBigList);
    }

    @Override
    public boolean addAll(ShortCollection shortCollection) {
        return this.addAll(this.size64(), shortCollection);
    }

    @Override
    public boolean addAll(ShortBigList shortBigList) {
        return this.addAll(this.size64(), shortBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Short s) {
        this.add(l, (short)s);
    }

    @Override
    @Deprecated
    public Short set(long l, Short s) {
        return this.set(l, (short)s);
    }

    @Override
    @Deprecated
    public Short get(long l) {
        return this.getShort(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Short)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Short)object);
    }

    @Override
    @Deprecated
    public Short remove(long l) {
        return this.removeShort(l);
    }

    @Override
    @Deprecated
    public void push(Short s) {
        this.push((short)s);
    }

    @Override
    @Deprecated
    public Short pop() {
        return this.popShort();
    }

    @Override
    @Deprecated
    public Short top() {
        return this.topShort();
    }

    @Override
    @Deprecated
    public Short peek(int n) {
        return this.peekShort(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ShortBigListIterator shortBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            short s = shortBigListIterator.nextShort();
            stringBuilder.append(String.valueOf(s));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ShortIterator iterator() {
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
        this.add(l, (Short)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Short)object);
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
        this.push((Short)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ShortSubList
    extends AbstractShortBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractShortBigList.class.desiredAssertionStatus();

        public ShortSubList(ShortBigList shortBigList, long l, long l2) {
            this.l = shortBigList;
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
        public boolean add(short s) {
            this.l.add(this.to, s);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, short s) {
            this.ensureIndex(l);
            this.l.add(this.from + l, s);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Short> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public short getShort(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getShort(this.from + l);
        }

        @Override
        public short removeShort(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeShort(this.from + l);
        }

        @Override
        public short set(long l, short s) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, s);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, short[][] sArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, sArray, l2, l3);
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
        public void addElements(long l, short[][] sArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, sArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ShortBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new ShortBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractShortBigList.class.desiredAssertionStatus();
                final long val$index;
                final ShortSubList this$0;
                {
                    this.this$0 = shortSubList;
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
                public short nextShort() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getShort(this.this$0.from + this.last);
                }

                @Override
                public short previousShort() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getShort(this.this$0.from + this.pos);
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
                public void add(short s) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, s);
                    this.last = -1L;
                    if (!$assertionsDisabled && !ShortSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(short s) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, s);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeShort(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !ShortSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ShortBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new ShortSubList(this, l, l2);
        }

        @Override
        public boolean rem(short s) {
            long l = this.indexOf(s);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeShort(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, ShortCollection shortCollection) {
            this.ensureIndex(l);
            return super.addAll(l, shortCollection);
        }

        @Override
        public boolean addAll(long l, ShortBigList shortBigList) {
            this.ensureIndex(l);
            return super.addAll(l, shortBigList);
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
            super.add(l, (Short)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Short)object);
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
        public ShortIterator iterator() {
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
            super.push((Short)object);
        }

        static boolean access$000(ShortSubList shortSubList) {
            return shortSubList.assertRange();
        }
    }
}

