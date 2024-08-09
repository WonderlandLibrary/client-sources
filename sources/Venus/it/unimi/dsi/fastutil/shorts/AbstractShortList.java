/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import it.unimi.dsi.fastutil.shorts.ShortStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShortList
extends AbstractShortCollection
implements ShortList,
ShortStack {
    protected AbstractShortList() {
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
    public void add(int n, short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(short s) {
        this.add(this.size(), s);
        return false;
    }

    @Override
    public short removeShort(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public short set(int n, short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Short> collection) {
        this.ensureIndex(n);
        Iterator<? extends Short> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (short)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Short> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public ShortListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public ShortListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ShortListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new ShortListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractShortList this$0;
            {
                this.this$0 = abstractShortList;
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
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(short s) {
                this.this$0.add(this.pos++, s);
                this.last = -1;
            }

            @Override
            public void set(short s) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, s);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeShort(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(short s) {
        return this.indexOf(s) >= 0;
    }

    @Override
    public int indexOf(short s) {
        ShortListIterator shortListIterator = this.listIterator();
        while (shortListIterator.hasNext()) {
            short s2 = shortListIterator.nextShort();
            if (s != s2) continue;
            return shortListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(short s) {
        ShortListIterator shortListIterator = this.listIterator(this.size());
        while (shortListIterator.hasPrevious()) {
            short s2 = shortListIterator.previousShort();
            if (s != s2) continue;
            return shortListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add((short)1);
            }
        } else {
            while (n2-- != n) {
                this.removeShort(n2);
            }
        }
    }

    @Override
    public ShortList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ShortSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        ShortListIterator shortListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            shortListIterator.nextShort();
            shortListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, short[] sArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > sArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + sArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, sArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, short[] sArray) {
        this.addElements(n, sArray, 0, sArray.length);
    }

    @Override
    public void getElements(int n, short[] sArray, int n2, int n3) {
        ShortListIterator shortListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > sArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + sArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            sArray[n2++] = shortListIterator.nextShort();
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
        ShortListIterator shortListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            short s = shortListIterator.nextShort();
            n = 31 * n + s;
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
        if (list instanceof ShortList) {
            ShortListIterator shortListIterator = this.listIterator();
            ShortListIterator shortListIterator2 = ((ShortList)list).listIterator();
            while (n-- != 0) {
                if (shortListIterator.nextShort() == shortListIterator2.nextShort()) continue;
                return true;
            }
            return false;
        }
        ShortListIterator shortListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(shortListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Short> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof ShortList) {
            ShortListIterator shortListIterator = this.listIterator();
            ShortListIterator shortListIterator2 = ((ShortList)list).listIterator();
            while (shortListIterator.hasNext() && shortListIterator2.hasNext()) {
                short s;
                short s2 = shortListIterator.nextShort();
                int n = Short.compare(s2, s = shortListIterator2.nextShort());
                if (n == 0) continue;
                return n;
            }
            return shortListIterator2.hasNext() ? -1 : (shortListIterator.hasNext() ? 1 : 0);
        }
        ShortListIterator shortListIterator = this.listIterator();
        ListIterator<? extends Short> listIterator2 = list.listIterator();
        while (shortListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)shortListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (shortListIterator.hasNext() ? 1 : 0);
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
        return this.removeShort(this.size() - 1);
    }

    @Override
    public short topShort() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getShort(this.size() - 1);
    }

    @Override
    public short peekShort(int n) {
        return this.getShort(this.size() - 1 - n);
    }

    @Override
    public boolean rem(short s) {
        int n = this.indexOf(s);
        if (n == -1) {
            return true;
        }
        this.removeShort(n);
        return false;
    }

    @Override
    public boolean addAll(int n, ShortCollection shortCollection) {
        this.ensureIndex(n);
        ShortIterator shortIterator = shortCollection.iterator();
        boolean bl = shortIterator.hasNext();
        while (shortIterator.hasNext()) {
            this.add(n++, shortIterator.nextShort());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, ShortList shortList) {
        return this.addAll(n, (ShortCollection)shortList);
    }

    @Override
    public boolean addAll(ShortCollection shortCollection) {
        return this.addAll(this.size(), shortCollection);
    }

    @Override
    public boolean addAll(ShortList shortList) {
        return this.addAll(this.size(), shortList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ShortListIterator shortListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            short s = shortListIterator.nextShort();
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
    public static class ShortSubList
    extends AbstractShortList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractShortList.class.desiredAssertionStatus();

        public ShortSubList(ShortList shortList, int n, int n2) {
            this.l = shortList;
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
        public boolean add(short s) {
            this.l.add(this.to, s);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, short s) {
            this.ensureIndex(n);
            this.l.add(this.from + n, s);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Short> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public short getShort(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getShort(this.from + n);
        }

        @Override
        public short removeShort(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeShort(this.from + n);
        }

        @Override
        public short set(int n, short s) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, s);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, short[] sArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, sArray, n2, n3);
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
        public void addElements(int n, short[] sArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, sArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ShortListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new ShortListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractShortList.class.desiredAssertionStatus();
                final int val$index;
                final ShortSubList this$0;
                {
                    this.this$0 = shortSubList;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(short s) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, s);
                    this.last = -1;
                    if (!$assertionsDisabled && !ShortSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(short s) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, s);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeShort(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !ShortSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ShortList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ShortSubList(this, n, n2);
        }

        @Override
        public boolean rem(short s) {
            int n = this.indexOf(s);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeShort(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, ShortCollection shortCollection) {
            this.ensureIndex(n);
            return super.addAll(n, shortCollection);
        }

        @Override
        public boolean addAll(int n, ShortList shortList) {
            this.ensureIndex(n);
            return super.addAll(n, shortList);
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
        public ShortIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(ShortSubList shortSubList) {
            return shortSubList.assertRange();
        }
    }
}

