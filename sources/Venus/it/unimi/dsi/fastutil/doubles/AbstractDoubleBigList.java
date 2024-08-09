/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleBigList;
import it.unimi.dsi.fastutil.doubles.DoubleBigListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDoubleBigList
extends AbstractDoubleCollection
implements DoubleBigList,
DoubleStack {
    protected AbstractDoubleBigList() {
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
    public void add(long l, double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(double d) {
        this.add(this.size64(), d);
        return false;
    }

    @Override
    public double removeDouble(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double set(long l, double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Double> collection) {
        this.ensureIndex(l);
        Iterator<? extends Double> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Double> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public DoubleBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public DoubleBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public DoubleBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new DoubleBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractDoubleBigList this$0;
            {
                this.this$0 = abstractDoubleBigList;
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
            public double nextDouble() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getDouble(this.last);
            }

            @Override
            public double previousDouble() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getDouble(this.pos);
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
            public void add(double d) {
                this.this$0.add(this.pos++, d);
                this.last = -1L;
            }

            @Override
            public void set(double d) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, d);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeDouble(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(double d) {
        return this.indexOf(d) >= 0L;
    }

    @Override
    public long indexOf(double d) {
        DoubleBigListIterator doubleBigListIterator = this.listIterator();
        while (doubleBigListIterator.hasNext()) {
            double d2 = doubleBigListIterator.nextDouble();
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) continue;
            return doubleBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(double d) {
        DoubleBigListIterator doubleBigListIterator = this.listIterator(this.size64());
        while (doubleBigListIterator.hasPrevious()) {
            double d2 = doubleBigListIterator.previousDouble();
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) continue;
            return doubleBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add(0.0);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public DoubleBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new DoubleSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        DoubleBigListIterator doubleBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            doubleBigListIterator.nextDouble();
            doubleBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, double[][] dArray, long l2, long l3) {
        this.ensureIndex(l);
        DoubleBigArrays.ensureOffsetLength(dArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, DoubleBigArrays.get(dArray, l2++));
        }
    }

    @Override
    public void addElements(long l, double[][] dArray) {
        this.addElements(l, dArray, 0L, DoubleBigArrays.length(dArray));
    }

    @Override
    public void getElements(long l, double[][] dArray, long l2, long l3) {
        DoubleBigListIterator doubleBigListIterator = this.listIterator(l);
        DoubleBigArrays.ensureOffsetLength(dArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            DoubleBigArrays.set(dArray, l2++, doubleBigListIterator.nextDouble());
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
        DoubleBigListIterator doubleBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            double d = doubleBigListIterator.nextDouble();
            n = 31 * n + HashCommon.double2int(d);
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
        if (bigList instanceof DoubleBigList) {
            DoubleBigListIterator doubleBigListIterator = this.listIterator();
            DoubleBigListIterator doubleBigListIterator2 = ((DoubleBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (doubleBigListIterator.nextDouble() == doubleBigListIterator2.nextDouble()) continue;
                return true;
            }
            return false;
        }
        DoubleBigListIterator doubleBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(doubleBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Double> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof DoubleBigList) {
            DoubleBigListIterator doubleBigListIterator = this.listIterator();
            DoubleBigListIterator doubleBigListIterator2 = ((DoubleBigList)bigList).listIterator();
            while (doubleBigListIterator.hasNext() && doubleBigListIterator2.hasNext()) {
                double d;
                double d2 = doubleBigListIterator.nextDouble();
                int n = Double.compare(d2, d = doubleBigListIterator2.nextDouble());
                if (n == 0) continue;
                return n;
            }
            return doubleBigListIterator2.hasNext() ? -1 : (doubleBigListIterator.hasNext() ? 1 : 0);
        }
        DoubleBigListIterator doubleBigListIterator = this.listIterator();
        BigListIterator<? extends Double> bigListIterator = bigList.listIterator();
        while (doubleBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)doubleBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (doubleBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(double d) {
        this.add(d);
    }

    @Override
    public double popDouble() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeDouble(this.size64() - 1L);
    }

    @Override
    public double topDouble() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getDouble(this.size64() - 1L);
    }

    @Override
    public double peekDouble(int n) {
        return this.getDouble(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(double d) {
        long l = this.indexOf(d);
        if (l == -1L) {
            return true;
        }
        this.removeDouble(l);
        return false;
    }

    @Override
    public boolean addAll(long l, DoubleCollection doubleCollection) {
        return this.addAll(l, (Collection<? extends Double>)doubleCollection);
    }

    @Override
    public boolean addAll(long l, DoubleBigList doubleBigList) {
        return this.addAll(l, (DoubleCollection)doubleBigList);
    }

    @Override
    public boolean addAll(DoubleCollection doubleCollection) {
        return this.addAll(this.size64(), doubleCollection);
    }

    @Override
    public boolean addAll(DoubleBigList doubleBigList) {
        return this.addAll(this.size64(), doubleBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Double d) {
        this.add(l, (double)d);
    }

    @Override
    @Deprecated
    public Double set(long l, Double d) {
        return this.set(l, (double)d);
    }

    @Override
    @Deprecated
    public Double get(long l) {
        return this.getDouble(l);
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf((Double)object);
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf((Double)object);
    }

    @Override
    @Deprecated
    public Double remove(long l) {
        return this.removeDouble(l);
    }

    @Override
    @Deprecated
    public void push(Double d) {
        this.push((double)d);
    }

    @Override
    @Deprecated
    public Double pop() {
        return this.popDouble();
    }

    @Override
    @Deprecated
    public Double top() {
        return this.topDouble();
    }

    @Override
    @Deprecated
    public Double peek(int n) {
        return this.peekDouble(n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        DoubleBigListIterator doubleBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            double d = doubleBigListIterator.nextDouble();
            stringBuilder.append(String.valueOf(d));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public DoubleIterator iterator() {
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
        this.add(l, (Double)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Double)object);
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
        this.push((Double)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class DoubleSubList
    extends AbstractDoubleBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractDoubleBigList.class.desiredAssertionStatus();

        public DoubleSubList(DoubleBigList doubleBigList, long l, long l2) {
            this.l = doubleBigList;
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
        public boolean add(double d) {
            this.l.add(this.to, d);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, double d) {
            this.ensureIndex(l);
            this.l.add(this.from + l, d);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Double> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public double getDouble(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getDouble(this.from + l);
        }

        @Override
        public double removeDouble(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeDouble(this.from + l);
        }

        @Override
        public double set(long l, double d) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, d);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, double[][] dArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, dArray, l2, l3);
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
        public void addElements(long l, double[][] dArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, dArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public DoubleBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new DoubleBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractDoubleBigList.class.desiredAssertionStatus();
                final long val$index;
                final DoubleSubList this$0;
                {
                    this.this$0 = doubleSubList;
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
                public double nextDouble() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getDouble(this.this$0.from + this.last);
                }

                @Override
                public double previousDouble() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getDouble(this.this$0.from + this.pos);
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
                public void add(double d) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, d);
                    this.last = -1L;
                    if (!$assertionsDisabled && !DoubleSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(double d) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, d);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeDouble(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !DoubleSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public DoubleBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new DoubleSubList(this, l, l2);
        }

        @Override
        public boolean rem(double d) {
            long l = this.indexOf(d);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeDouble(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, DoubleCollection doubleCollection) {
            this.ensureIndex(l);
            return super.addAll(l, doubleCollection);
        }

        @Override
        public boolean addAll(long l, DoubleBigList doubleBigList) {
            this.ensureIndex(l);
            return super.addAll(l, doubleBigList);
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
            super.add(l, (Double)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Double)object);
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
        public DoubleIterator iterator() {
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
            super.push((Double)object);
        }

        static boolean access$000(DoubleSubList doubleSubList) {
            return doubleSubList.assertRange();
        }
    }
}

