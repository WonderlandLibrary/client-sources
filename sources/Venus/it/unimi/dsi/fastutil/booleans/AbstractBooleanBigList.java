/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
import it.unimi.dsi.fastutil.booleans.BooleanBigList;
import it.unimi.dsi.fastutil.booleans.BooleanBigListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractBooleanBigList
extends AbstractBooleanCollection
implements BooleanBigList,
BooleanStack {
    protected AbstractBooleanBigList() {
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
    public void add(long l, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(boolean bl) {
        this.add(this.size64(), bl);
        return false;
    }

    @Override
    public boolean removeBoolean(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean set(long l, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Boolean> collection) {
        this.ensureIndex(l);
        Iterator<? extends Boolean> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public BooleanBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public BooleanBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public BooleanBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new BooleanBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractBooleanBigList this$0;
            {
                this.this$0 = abstractBooleanBigList;
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
            public boolean nextBoolean() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getBoolean(this.last);
            }

            @Override
            public boolean previousBoolean() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getBoolean(this.pos);
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
            public void add(boolean bl) {
                this.this$0.add(this.pos++, bl);
                this.last = -1L;
            }

            @Override
            public void set(boolean bl) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, bl);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeBoolean(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(boolean bl) {
        return this.indexOf(bl) >= 0L;
    }

    @Override
    public long indexOf(boolean bl) {
        BooleanBigListIterator booleanBigListIterator = this.listIterator();
        while (booleanBigListIterator.hasNext()) {
            boolean bl2 = booleanBigListIterator.nextBoolean();
            if (bl != bl2) continue;
            return booleanBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(boolean bl) {
        BooleanBigListIterator booleanBigListIterator = this.listIterator(this.size64());
        while (booleanBigListIterator.hasPrevious()) {
            boolean bl2 = booleanBigListIterator.previousBoolean();
            if (bl != bl2) continue;
            return booleanBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add(true);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public BooleanBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new BooleanSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        BooleanBigListIterator booleanBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            booleanBigListIterator.nextBoolean();
            booleanBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, boolean[][] blArray, long l2, long l3) {
        this.ensureIndex(l);
        BooleanBigArrays.ensureOffsetLength(blArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, BooleanBigArrays.get(blArray, l2++));
        }
    }

    @Override
    public void addElements(long l, boolean[][] blArray) {
        this.addElements(l, blArray, 0L, BooleanBigArrays.length(blArray));
    }

    @Override
    public void getElements(long l, boolean[][] blArray, long l2, long l3) {
        BooleanBigListIterator booleanBigListIterator = this.listIterator(l);
        BooleanBigArrays.ensureOffsetLength(blArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            BooleanBigArrays.set(blArray, l2++, booleanBigListIterator.nextBoolean());
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
        BooleanBigListIterator booleanBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            boolean bl = booleanBigListIterator.nextBoolean();
            n = 31 * n + (bl ? 1231 : 1237);
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
        if (bigList instanceof BooleanBigList) {
            BooleanBigListIterator booleanBigListIterator = this.listIterator();
            BooleanBigListIterator booleanBigListIterator2 = ((BooleanBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (booleanBigListIterator.nextBoolean() == booleanBigListIterator2.nextBoolean()) continue;
                return true;
            }
            return false;
        }
        BooleanBigListIterator booleanBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(booleanBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Boolean> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof BooleanBigList) {
            BooleanBigListIterator booleanBigListIterator = this.listIterator();
            BooleanBigListIterator booleanBigListIterator2 = ((BooleanBigList)bigList).listIterator();
            while (booleanBigListIterator.hasNext() && booleanBigListIterator2.hasNext()) {
                boolean bl;
                boolean bl2 = booleanBigListIterator.nextBoolean();
                int n = Boolean.compare(bl2, bl = booleanBigListIterator2.nextBoolean());
                if (n == 0) continue;
                return n;
            }
            return booleanBigListIterator2.hasNext() ? -1 : (booleanBigListIterator.hasNext() ? 1 : 0);
        }
        BooleanBigListIterator booleanBigListIterator = this.listIterator();
        BigListIterator<? extends Boolean> bigListIterator = bigList.listIterator();
        while (booleanBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)booleanBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (booleanBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(boolean bl) {
        this.add(bl);
    }

    @Override
    public boolean popBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeBoolean(this.size64() - 1L);
    }

    @Override
    public boolean topBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getBoolean(this.size64() - 1L);
    }

    @Override
    public boolean peekBoolean(int n) {
        return this.getBoolean(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(boolean bl) {
        long l = this.indexOf(bl);
        if (l == -1L) {
            return true;
        }
        this.removeBoolean(l);
        return false;
    }

    @Override
    public boolean addAll(long l, BooleanCollection booleanCollection) {
        return this.addAll(l, (Collection<? extends Boolean>)booleanCollection);
    }

    @Override
    public boolean addAll(long l, BooleanBigList booleanBigList) {
        return this.addAll(l, (BooleanCollection)booleanBigList);
    }

    @Override
    public boolean addAll(BooleanCollection booleanCollection) {
        return this.addAll(this.size64(), booleanCollection);
    }

    @Override
    public boolean addAll(BooleanBigList booleanBigList) {
        return this.addAll(this.size64(), booleanBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Boolean bl) {
        this.add(l, (boolean)bl);
    }

    @Override
    @Deprecated
    public Boolean set(long l, Boolean bl) {
        return this.set(l, (boolean)bl);
    }

    @Override
    @Deprecated
    public Boolean get(long l) {
        return this.getBoolean(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Boolean)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Boolean)object);
    }

    @Override
    @Deprecated
    public Boolean remove(long l) {
        return this.removeBoolean(l);
    }

    @Override
    @Deprecated
    public void push(Boolean bl) {
        this.push((boolean)bl);
    }

    @Override
    @Deprecated
    public Boolean pop() {
        return this.popBoolean();
    }

    @Override
    @Deprecated
    public Boolean top() {
        return this.topBoolean();
    }

    @Override
    @Deprecated
    public Boolean peek(int n) {
        return this.peekBoolean(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        BooleanBigListIterator booleanBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            boolean bl2 = booleanBigListIterator.nextBoolean();
            stringBuilder.append(String.valueOf(bl2));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public BooleanIterator iterator() {
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
        this.add(l, (Boolean)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Boolean)object);
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
        this.push((Boolean)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class BooleanSubList
    extends AbstractBooleanBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractBooleanBigList.class.desiredAssertionStatus();

        public BooleanSubList(BooleanBigList booleanBigList, long l, long l2) {
            this.l = booleanBigList;
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
        public boolean add(boolean bl) {
            this.l.add(this.to, bl);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, boolean bl) {
            this.ensureIndex(l);
            this.l.add(this.from + l, bl);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Boolean> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public boolean getBoolean(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getBoolean(this.from + l);
        }

        @Override
        public boolean removeBoolean(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeBoolean(this.from + l);
        }

        @Override
        public boolean set(long l, boolean bl) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, bl);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, boolean[][] blArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, blArray, l2, l3);
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
        public void addElements(long l, boolean[][] blArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, blArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public BooleanBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new BooleanBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractBooleanBigList.class.desiredAssertionStatus();
                final long val$index;
                final BooleanSubList this$0;
                {
                    this.this$0 = booleanSubList;
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
                public boolean nextBoolean() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getBoolean(this.this$0.from + this.last);
                }

                @Override
                public boolean previousBoolean() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getBoolean(this.this$0.from + this.pos);
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
                public void add(boolean bl) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, bl);
                    this.last = -1L;
                    if (!$assertionsDisabled && !BooleanSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(boolean bl) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, bl);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeBoolean(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !BooleanSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public BooleanBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new BooleanSubList(this, l, l2);
        }

        @Override
        public boolean rem(boolean bl) {
            long l = this.indexOf(bl);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeBoolean(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, BooleanCollection booleanCollection) {
            this.ensureIndex(l);
            return super.addAll(l, booleanCollection);
        }

        @Override
        public boolean addAll(long l, BooleanBigList booleanBigList) {
            this.ensureIndex(l);
            return super.addAll(l, booleanBigList);
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
            super.add(l, (Boolean)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Boolean)object);
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
        public BooleanIterator iterator() {
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
            super.push((Boolean)object);
        }

        static boolean access$000(BooleanSubList booleanSubList) {
            return booleanSubList.assertRange();
        }
    }
}

