/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongBigArrays;
import it.unimi.dsi.fastutil.longs.LongBigList;
import it.unimi.dsi.fastutil.longs.LongBigListIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractLongBigList
extends AbstractLongCollection
implements LongBigList,
LongStack {
    protected AbstractLongBigList() {
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
    public void add(long l, long l2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(long l) {
        this.add(this.size64(), l);
        return false;
    }

    @Override
    public long removeLong(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long set(long l, long l2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Long> collection) {
        this.ensureIndex(l);
        Iterator<? extends Long> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Long> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public LongBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public LongBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public LongBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new LongBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractLongBigList this$0;
            {
                this.this$0 = abstractLongBigList;
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
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getLong(this.last);
            }

            @Override
            public long previousLong() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getLong(this.pos);
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
            public void add(long l) {
                this.this$0.add(this.pos++, l);
                this.last = -1L;
            }

            @Override
            public void set(long l) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, l);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeLong(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(long l) {
        return this.indexOf(l) >= 0L;
    }

    @Override
    public long indexOf(long l) {
        LongBigListIterator longBigListIterator = this.listIterator();
        while (longBigListIterator.hasNext()) {
            long l2 = longBigListIterator.nextLong();
            if (l != l2) continue;
            return longBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(long l) {
        LongBigListIterator longBigListIterator = this.listIterator(this.size64());
        while (longBigListIterator.hasPrevious()) {
            long l2 = longBigListIterator.previousLong();
            if (l != l2) continue;
            return longBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add(0L);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public LongBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new LongSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        LongBigListIterator longBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            longBigListIterator.nextLong();
            longBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, long[][] lArray, long l2, long l3) {
        this.ensureIndex(l);
        LongBigArrays.ensureOffsetLength(lArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, LongBigArrays.get(lArray, l2++));
        }
    }

    @Override
    public void addElements(long l, long[][] lArray) {
        this.addElements(l, lArray, 0L, LongBigArrays.length(lArray));
    }

    @Override
    public void getElements(long l, long[][] lArray, long l2, long l3) {
        LongBigListIterator longBigListIterator = this.listIterator(l);
        LongBigArrays.ensureOffsetLength(lArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            LongBigArrays.set(lArray, l2++, longBigListIterator.nextLong());
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
        LongBigListIterator longBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            long l2 = longBigListIterator.nextLong();
            n = 31 * n + HashCommon.long2int(l2);
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
        if (bigList instanceof LongBigList) {
            LongBigListIterator longBigListIterator = this.listIterator();
            LongBigListIterator longBigListIterator2 = ((LongBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (longBigListIterator.nextLong() == longBigListIterator2.nextLong()) continue;
                return true;
            }
            return false;
        }
        LongBigListIterator longBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(longBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Long> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof LongBigList) {
            LongBigListIterator longBigListIterator = this.listIterator();
            LongBigListIterator longBigListIterator2 = ((LongBigList)bigList).listIterator();
            while (longBigListIterator.hasNext() && longBigListIterator2.hasNext()) {
                long l;
                long l2 = longBigListIterator.nextLong();
                int n = Long.compare(l2, l = longBigListIterator2.nextLong());
                if (n == 0) continue;
                return n;
            }
            return longBigListIterator2.hasNext() ? -1 : (longBigListIterator.hasNext() ? 1 : 0);
        }
        LongBigListIterator longBigListIterator = this.listIterator();
        BigListIterator<? extends Long> bigListIterator = bigList.listIterator();
        while (longBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)longBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (longBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(long l) {
        this.add(l);
    }

    @Override
    public long popLong() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeLong(this.size64() - 1L);
    }

    @Override
    public long topLong() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getLong(this.size64() - 1L);
    }

    @Override
    public long peekLong(int n) {
        return this.getLong(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(long l) {
        long l2 = this.indexOf(l);
        if (l2 == -1L) {
            return true;
        }
        this.removeLong(l2);
        return false;
    }

    @Override
    public boolean addAll(long l, LongCollection longCollection) {
        return this.addAll(l, (Collection<? extends Long>)longCollection);
    }

    @Override
    public boolean addAll(long l, LongBigList longBigList) {
        return this.addAll(l, (LongCollection)longBigList);
    }

    @Override
    public boolean addAll(LongCollection longCollection) {
        return this.addAll(this.size64(), longCollection);
    }

    @Override
    public boolean addAll(LongBigList longBigList) {
        return this.addAll(this.size64(), longBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Long l2) {
        this.add(l, (long)l2);
    }

    @Override
    @Deprecated
    public Long set(long l, Long l2) {
        return this.set(l, (long)l2);
    }

    @Override
    @Deprecated
    public Long get(long l) {
        return this.getLong(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Long)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Long)object);
    }

    @Override
    @Deprecated
    public Long remove(long l) {
        return this.removeLong(l);
    }

    @Override
    @Deprecated
    public void push(Long l) {
        this.push((long)l);
    }

    @Override
    @Deprecated
    public Long pop() {
        return this.popLong();
    }

    @Override
    @Deprecated
    public Long top() {
        return this.topLong();
    }

    @Override
    @Deprecated
    public Long peek(int n) {
        return this.peekLong(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        LongBigListIterator longBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            long l2 = longBigListIterator.nextLong();
            stringBuilder.append(String.valueOf(l2));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public LongIterator iterator() {
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
        this.add(l, (Long)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Long)object);
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
        this.push((Long)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class LongSubList
    extends AbstractLongBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractLongBigList.class.desiredAssertionStatus();

        public LongSubList(LongBigList longBigList, long l, long l2) {
            this.l = longBigList;
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
        public boolean add(long l) {
            this.l.add(this.to, l);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, long l2) {
            this.ensureIndex(l);
            this.l.add(this.from + l, l2);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Long> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public long getLong(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getLong(this.from + l);
        }

        @Override
        public long removeLong(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeLong(this.from + l);
        }

        @Override
        public long set(long l, long l2) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, l2);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, long[][] lArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, lArray, l2, l3);
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
        public void addElements(long l, long[][] lArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, lArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public LongBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new LongBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractLongBigList.class.desiredAssertionStatus();
                final long val$index;
                final LongSubList this$0;
                {
                    this.this$0 = longSubList;
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
                public long nextLong() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getLong(this.this$0.from + this.last);
                }

                @Override
                public long previousLong() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getLong(this.this$0.from + this.pos);
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
                public void add(long l) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, l);
                    this.last = -1L;
                    if (!$assertionsDisabled && !LongSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(long l) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, l);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeLong(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !LongSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public LongBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new LongSubList(this, l, l2);
        }

        @Override
        public boolean rem(long l) {
            long l2 = this.indexOf(l);
            if (l2 == -1L) {
                return true;
            }
            --this.to;
            this.l.removeLong(this.from + l2);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, LongCollection longCollection) {
            this.ensureIndex(l);
            return super.addAll(l, longCollection);
        }

        @Override
        public boolean addAll(long l, LongBigList longBigList) {
            this.ensureIndex(l);
            return super.addAll(l, longBigList);
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
            super.add(l, (Long)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Long)object);
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
        public LongIterator iterator() {
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
            super.push((Long)object);
        }

        static boolean access$000(LongSubList longSubList) {
            return longSubList.assertRange();
        }
    }
}

