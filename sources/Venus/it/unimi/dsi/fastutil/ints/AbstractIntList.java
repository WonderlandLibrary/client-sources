/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractIntList
extends AbstractIntCollection
implements IntList,
IntStack {
    protected AbstractIntList() {
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
    public void add(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(int n) {
        this.add(this.size(), n);
        return false;
    }

    @Override
    public int removeInt(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int set(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Integer> collection) {
        this.ensureIndex(n);
        Iterator<? extends Integer> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (int)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public IntListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public IntListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public IntListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new IntListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractIntList this$0;
            {
                this.this$0 = abstractIntList;
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
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(int n) {
                this.this$0.add(this.pos++, n);
                this.last = -1;
            }

            @Override
            public void set(int n) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(int n) {
        return this.indexOf(n) >= 0;
    }

    @Override
    public int indexOf(int n) {
        IntListIterator intListIterator = this.listIterator();
        while (intListIterator.hasNext()) {
            int n2 = intListIterator.nextInt();
            if (n != n2) continue;
            return intListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(int n) {
        IntListIterator intListIterator = this.listIterator(this.size());
        while (intListIterator.hasPrevious()) {
            int n2 = intListIterator.previousInt();
            if (n != n2) continue;
            return intListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(1);
            }
        } else {
            while (n2-- != n) {
                this.removeInt(n2);
            }
        }
    }

    @Override
    public IntList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new IntSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        IntListIterator intListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            intListIterator.nextInt();
            intListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, int[] nArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > nArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + nArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, nArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, int[] nArray) {
        this.addElements(n, nArray, 0, nArray.length);
    }

    @Override
    public void getElements(int n, int[] nArray, int n2, int n3) {
        IntListIterator intListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > nArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + nArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            nArray[n2++] = intListIterator.nextInt();
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
        IntListIterator intListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            int n3 = intListIterator.nextInt();
            n = 31 * n + n3;
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
        if (list instanceof IntList) {
            IntListIterator intListIterator = this.listIterator();
            IntListIterator intListIterator2 = ((IntList)list).listIterator();
            while (n-- != 0) {
                if (intListIterator.nextInt() == intListIterator2.nextInt()) continue;
                return true;
            }
            return false;
        }
        IntListIterator intListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(intListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Integer> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof IntList) {
            IntListIterator intListIterator = this.listIterator();
            IntListIterator intListIterator2 = ((IntList)list).listIterator();
            while (intListIterator.hasNext() && intListIterator2.hasNext()) {
                int n;
                int n2 = intListIterator.nextInt();
                int n3 = Integer.compare(n2, n = intListIterator2.nextInt());
                if (n3 == 0) continue;
                return n3;
            }
            return intListIterator2.hasNext() ? -1 : (intListIterator.hasNext() ? 1 : 0);
        }
        IntListIterator intListIterator = this.listIterator();
        ListIterator<? extends Integer> listIterator2 = list.listIterator();
        while (intListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)intListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (intListIterator.hasNext() ? 1 : 0);
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
        return this.removeInt(this.size() - 1);
    }

    @Override
    public int topInt() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getInt(this.size() - 1);
    }

    @Override
    public int peekInt(int n) {
        return this.getInt(this.size() - 1 - n);
    }

    @Override
    public boolean rem(int n) {
        int n2 = this.indexOf(n);
        if (n2 == -1) {
            return true;
        }
        this.removeInt(n2);
        return false;
    }

    @Override
    public boolean addAll(int n, IntCollection intCollection) {
        this.ensureIndex(n);
        IntIterator intIterator = intCollection.iterator();
        boolean bl = intIterator.hasNext();
        while (intIterator.hasNext()) {
            this.add(n++, intIterator.nextInt());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, IntList intList) {
        return this.addAll(n, (IntCollection)intList);
    }

    @Override
    public boolean addAll(IntCollection intCollection) {
        return this.addAll(this.size(), intCollection);
    }

    @Override
    public boolean addAll(IntList intList) {
        return this.addAll(this.size(), intList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        IntListIterator intListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            int n2 = intListIterator.nextInt();
            stringBuilder.append(String.valueOf(n2));
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
    public static class IntSubList
    extends AbstractIntList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();

        public IntSubList(IntList intList, int n, int n2) {
            this.l = intList;
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
        public boolean add(int n) {
            this.l.add(this.to, n);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, int n2) {
            this.ensureIndex(n);
            this.l.add(this.from + n, n2);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Integer> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public int getInt(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getInt(this.from + n);
        }

        @Override
        public int removeInt(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeInt(this.from + n);
        }

        @Override
        public int set(int n, int n2) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, n2);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, int[] nArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, nArray, n2, n3);
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
        public void addElements(int n, int[] nArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, nArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public IntListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new IntListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
                final int val$index;
                final IntSubList this$0;
                {
                    this.this$0 = intSubList;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(int n) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, n);
                    this.last = -1;
                    if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(int n) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, n);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeInt(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !IntSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public IntList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new IntSubList(this, n, n2);
        }

        @Override
        public boolean rem(int n) {
            int n2 = this.indexOf(n);
            if (n2 == -1) {
                return true;
            }
            --this.to;
            this.l.removeInt(this.from + n2);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, IntCollection intCollection) {
            this.ensureIndex(n);
            return super.addAll(n, intCollection);
        }

        @Override
        public boolean addAll(int n, IntList intList) {
            this.ensureIndex(n);
            return super.addAll(n, intList);
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
        public IntIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(IntSubList intSubList) {
            return intSubList.assertRange();
        }
    }
}

