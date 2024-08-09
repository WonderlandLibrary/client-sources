/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.booleans.BooleanStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractBooleanList
extends AbstractBooleanCollection
implements BooleanList,
BooleanStack {
    protected AbstractBooleanList() {
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
    public void add(int n, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(boolean bl) {
        this.add(this.size(), bl);
        return false;
    }

    @Override
    public boolean removeBoolean(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean set(int n, boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Boolean> collection) {
        this.ensureIndex(n);
        Iterator<? extends Boolean> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (boolean)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public BooleanListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public BooleanListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public BooleanListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new BooleanListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractBooleanList this$0;
            {
                this.this$0 = abstractBooleanList;
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

    @Override
    public boolean contains(boolean bl) {
        return this.indexOf(bl) >= 0;
    }

    @Override
    public int indexOf(boolean bl) {
        BooleanListIterator booleanListIterator = this.listIterator();
        while (booleanListIterator.hasNext()) {
            boolean bl2 = booleanListIterator.nextBoolean();
            if (bl != bl2) continue;
            return booleanListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(boolean bl) {
        BooleanListIterator booleanListIterator = this.listIterator(this.size());
        while (booleanListIterator.hasPrevious()) {
            boolean bl2 = booleanListIterator.previousBoolean();
            if (bl != bl2) continue;
            return booleanListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(true);
            }
        } else {
            while (n2-- != n) {
                this.removeBoolean(n2);
            }
        }
    }

    @Override
    public BooleanList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new BooleanSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        BooleanListIterator booleanListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            booleanListIterator.nextBoolean();
            booleanListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, boolean[] blArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > blArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + blArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, blArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, boolean[] blArray) {
        this.addElements(n, blArray, 0, blArray.length);
    }

    @Override
    public void getElements(int n, boolean[] blArray, int n2, int n3) {
        BooleanListIterator booleanListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > blArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + blArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            blArray[n2++] = booleanListIterator.nextBoolean();
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
        BooleanListIterator booleanListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            boolean bl = booleanListIterator.nextBoolean();
            n = 31 * n + (bl ? 1231 : 1237);
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
        if (list instanceof BooleanList) {
            BooleanListIterator booleanListIterator = this.listIterator();
            BooleanListIterator booleanListIterator2 = ((BooleanList)list).listIterator();
            while (n-- != 0) {
                if (booleanListIterator.nextBoolean() == booleanListIterator2.nextBoolean()) continue;
                return true;
            }
            return false;
        }
        BooleanListIterator booleanListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(booleanListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Boolean> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof BooleanList) {
            BooleanListIterator booleanListIterator = this.listIterator();
            BooleanListIterator booleanListIterator2 = ((BooleanList)list).listIterator();
            while (booleanListIterator.hasNext() && booleanListIterator2.hasNext()) {
                boolean bl;
                boolean bl2 = booleanListIterator.nextBoolean();
                int n = Boolean.compare(bl2, bl = booleanListIterator2.nextBoolean());
                if (n == 0) continue;
                return n;
            }
            return booleanListIterator2.hasNext() ? -1 : (booleanListIterator.hasNext() ? 1 : 0);
        }
        BooleanListIterator booleanListIterator = this.listIterator();
        ListIterator<? extends Boolean> listIterator2 = list.listIterator();
        while (booleanListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)booleanListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (booleanListIterator.hasNext() ? 1 : 0);
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
        return this.removeBoolean(this.size() - 1);
    }

    @Override
    public boolean topBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getBoolean(this.size() - 1);
    }

    @Override
    public boolean peekBoolean(int n) {
        return this.getBoolean(this.size() - 1 - n);
    }

    @Override
    public boolean rem(boolean bl) {
        int n = this.indexOf(bl);
        if (n == -1) {
            return true;
        }
        this.removeBoolean(n);
        return false;
    }

    @Override
    public boolean addAll(int n, BooleanCollection booleanCollection) {
        this.ensureIndex(n);
        BooleanIterator booleanIterator = booleanCollection.iterator();
        boolean bl = booleanIterator.hasNext();
        while (booleanIterator.hasNext()) {
            this.add(n++, booleanIterator.nextBoolean());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, BooleanList booleanList) {
        return this.addAll(n, (BooleanCollection)booleanList);
    }

    @Override
    public boolean addAll(BooleanCollection booleanCollection) {
        return this.addAll(this.size(), booleanCollection);
    }

    @Override
    public boolean addAll(BooleanList booleanList) {
        return this.addAll(this.size(), booleanList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        BooleanListIterator booleanListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            boolean bl2 = booleanListIterator.nextBoolean();
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
    public static class BooleanSubList
    extends AbstractBooleanList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final BooleanList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractBooleanList.class.desiredAssertionStatus();

        public BooleanSubList(BooleanList booleanList, int n, int n2) {
            this.l = booleanList;
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
        public boolean add(boolean bl) {
            this.l.add(this.to, bl);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, boolean bl) {
            this.ensureIndex(n);
            this.l.add(this.from + n, bl);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Boolean> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public boolean getBoolean(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getBoolean(this.from + n);
        }

        @Override
        public boolean removeBoolean(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeBoolean(this.from + n);
        }

        @Override
        public boolean set(int n, boolean bl) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, bl);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, boolean[] blArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, blArray, n2, n3);
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
        public void addElements(int n, boolean[] blArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, blArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public BooleanListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new BooleanListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractBooleanList.class.desiredAssertionStatus();
                final int val$index;
                final BooleanSubList this$0;
                {
                    this.this$0 = booleanSubList;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(boolean bl) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, bl);
                    this.last = -1;
                    if (!$assertionsDisabled && !BooleanSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
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
                    if (!$assertionsDisabled && !BooleanSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public BooleanList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new BooleanSubList(this, n, n2);
        }

        @Override
        public boolean rem(boolean bl) {
            int n = this.indexOf(bl);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeBoolean(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, BooleanCollection booleanCollection) {
            this.ensureIndex(n);
            return super.addAll(n, booleanCollection);
        }

        @Override
        public boolean addAll(int n, BooleanList booleanList) {
            this.ensureIndex(n);
            return super.addAll(n, booleanList);
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
        public BooleanIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(BooleanSubList booleanSubList) {
            return booleanSubList.assertRange();
        }
    }
}

