/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import it.unimi.dsi.fastutil.doubles.DoubleStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDoubleList
extends AbstractDoubleCollection
implements DoubleList,
DoubleStack {
    protected AbstractDoubleList() {
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
    public void add(int n, double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(double d) {
        this.add(this.size(), d);
        return false;
    }

    @Override
    public double removeDouble(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double set(int n, double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Double> collection) {
        this.ensureIndex(n);
        Iterator<? extends Double> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (double)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Double> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public DoubleListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public DoubleListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public DoubleListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new DoubleListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractDoubleList this$0;
            {
                this.this$0 = abstractDoubleList;
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
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(double d) {
                this.this$0.add(this.pos++, d);
                this.last = -1;
            }

            @Override
            public void set(double d) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, d);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeDouble(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(double d) {
        return this.indexOf(d) >= 0;
    }

    @Override
    public int indexOf(double d) {
        DoubleListIterator doubleListIterator = this.listIterator();
        while (doubleListIterator.hasNext()) {
            double d2 = doubleListIterator.nextDouble();
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) continue;
            return doubleListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(double d) {
        DoubleListIterator doubleListIterator = this.listIterator(this.size());
        while (doubleListIterator.hasPrevious()) {
            double d2 = doubleListIterator.previousDouble();
            if (Double.doubleToLongBits(d) != Double.doubleToLongBits(d2)) continue;
            return doubleListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(0.0);
            }
        } else {
            while (n2-- != n) {
                this.removeDouble(n2);
            }
        }
    }

    @Override
    public DoubleList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new DoubleSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        DoubleListIterator doubleListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            doubleListIterator.nextDouble();
            doubleListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, double[] dArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > dArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + dArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, dArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, double[] dArray) {
        this.addElements(n, dArray, 0, dArray.length);
    }

    @Override
    public void getElements(int n, double[] dArray, int n2, int n3) {
        DoubleListIterator doubleListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > dArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + dArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            dArray[n2++] = doubleListIterator.nextDouble();
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
        DoubleListIterator doubleListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            double d = doubleListIterator.nextDouble();
            n = 31 * n + HashCommon.double2int(d);
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
        if (list instanceof DoubleList) {
            DoubleListIterator doubleListIterator = this.listIterator();
            DoubleListIterator doubleListIterator2 = ((DoubleList)list).listIterator();
            while (n-- != 0) {
                if (doubleListIterator.nextDouble() == doubleListIterator2.nextDouble()) continue;
                return true;
            }
            return false;
        }
        DoubleListIterator doubleListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(doubleListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Double> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof DoubleList) {
            DoubleListIterator doubleListIterator = this.listIterator();
            DoubleListIterator doubleListIterator2 = ((DoubleList)list).listIterator();
            while (doubleListIterator.hasNext() && doubleListIterator2.hasNext()) {
                double d;
                double d2 = doubleListIterator.nextDouble();
                int n = Double.compare(d2, d = doubleListIterator2.nextDouble());
                if (n == 0) continue;
                return n;
            }
            return doubleListIterator2.hasNext() ? -1 : (doubleListIterator.hasNext() ? 1 : 0);
        }
        DoubleListIterator doubleListIterator = this.listIterator();
        ListIterator<? extends Double> listIterator2 = list.listIterator();
        while (doubleListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)doubleListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (doubleListIterator.hasNext() ? 1 : 0);
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
        return this.removeDouble(this.size() - 1);
    }

    @Override
    public double topDouble() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getDouble(this.size() - 1);
    }

    @Override
    public double peekDouble(int n) {
        return this.getDouble(this.size() - 1 - n);
    }

    @Override
    public boolean rem(double d) {
        int n = this.indexOf(d);
        if (n == -1) {
            return true;
        }
        this.removeDouble(n);
        return false;
    }

    @Override
    public boolean addAll(int n, DoubleCollection doubleCollection) {
        this.ensureIndex(n);
        DoubleIterator doubleIterator = doubleCollection.iterator();
        boolean bl = doubleIterator.hasNext();
        while (doubleIterator.hasNext()) {
            this.add(n++, doubleIterator.nextDouble());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, DoubleList doubleList) {
        return this.addAll(n, (DoubleCollection)doubleList);
    }

    @Override
    public boolean addAll(DoubleCollection doubleCollection) {
        return this.addAll(this.size(), doubleCollection);
    }

    @Override
    public boolean addAll(DoubleList doubleList) {
        return this.addAll(this.size(), doubleList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        DoubleListIterator doubleListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            double d = doubleListIterator.nextDouble();
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
    public static class DoubleSubList
    extends AbstractDoubleList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractDoubleList.class.desiredAssertionStatus();

        public DoubleSubList(DoubleList doubleList, int n, int n2) {
            this.l = doubleList;
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
        public boolean add(double d) {
            this.l.add(this.to, d);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, double d) {
            this.ensureIndex(n);
            this.l.add(this.from + n, d);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Double> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public double getDouble(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getDouble(this.from + n);
        }

        @Override
        public double removeDouble(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeDouble(this.from + n);
        }

        @Override
        public double set(int n, double d) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, d);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, double[] dArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, dArray, n2, n3);
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
        public void addElements(int n, double[] dArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, dArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public DoubleListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new DoubleListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractDoubleList.class.desiredAssertionStatus();
                final int val$index;
                final DoubleSubList this$0;
                {
                    this.this$0 = doubleSubList;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(double d) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, d);
                    this.last = -1;
                    if (!$assertionsDisabled && !DoubleSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(double d) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, d);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeDouble(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !DoubleSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public DoubleList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new DoubleSubList(this, n, n2);
        }

        @Override
        public boolean rem(double d) {
            int n = this.indexOf(d);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeDouble(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, DoubleCollection doubleCollection) {
            this.ensureIndex(n);
            return super.addAll(n, doubleCollection);
        }

        @Override
        public boolean addAll(int n, DoubleList doubleList) {
            this.ensureIndex(n);
            return super.addAll(n, doubleList);
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
        public DoubleIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(DoubleSubList doubleSubList) {
            return doubleSubList.assertRange();
        }
    }
}

