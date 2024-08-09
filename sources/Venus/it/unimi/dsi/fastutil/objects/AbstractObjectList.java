/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractObjectList<K>
extends AbstractObjectCollection<K>
implements ObjectList<K>,
Stack<K> {
    protected AbstractObjectList() {
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
    public void add(int n, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(K k) {
        this.add(this.size(), k);
        return false;
    }

    @Override
    public K remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public K set(int n, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends K> collection) {
        this.ensureIndex(n);
        Iterator<K> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends K> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public ObjectListIterator<K> iterator() {
        return this.listIterator();
    }

    @Override
    public ObjectListIterator<K> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ObjectListIterator<K> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<K>(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractObjectList this$0;
            {
                this.this$0 = abstractObjectList;
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
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.get(this.last);
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.get(this.pos);
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
            public void add(K k) {
                this.this$0.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(K k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    public int indexOf(Object object) {
        ListIterator listIterator2 = this.listIterator();
        while (listIterator2.hasNext()) {
            Object e = listIterator2.next();
            if (!Objects.equals(object, e)) continue;
            return listIterator2.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(Object object) {
        ListIterator listIterator2 = this.listIterator(this.size());
        while (listIterator2.hasPrevious()) {
            Object k = listIterator2.previous();
            if (!Objects.equals(object, k)) continue;
            return listIterator2.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add((K)null);
            }
        } else {
            while (n2-- != n) {
                this.remove(n2);
            }
        }
    }

    @Override
    public ObjectList<K> subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ObjectSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        ListIterator listIterator2 = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            listIterator2.next();
            listIterator2.remove();
        }
    }

    @Override
    public void addElements(int n, K[] KArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > KArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + KArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, KArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, K[] KArray) {
        this.addElements(n, KArray, 0, KArray.length);
    }

    @Override
    public void getElements(int n, Object[] objectArray, int n2, int n3) {
        ListIterator listIterator2 = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > objectArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + objectArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            objectArray[n2++] = listIterator2.next();
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
        ObjectIterator objectIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            Object e = objectIterator.next();
            n = 31 * n + (e == null ? 0 : e.hashCode());
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
        ListIterator listIterator2 = this.listIterator();
        ListIterator listIterator3 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(listIterator2.next(), listIterator3.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends K> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof ObjectList) {
            ListIterator listIterator2 = this.listIterator();
            ListIterator listIterator3 = ((ObjectList)list).listIterator();
            while (listIterator2.hasNext() && listIterator3.hasNext()) {
                Object e;
                Object e2 = listIterator2.next();
                int n = ((Comparable)e2).compareTo(e = listIterator3.next());
                if (n == 0) continue;
                return n;
            }
            return listIterator3.hasNext() ? -1 : (listIterator2.hasNext() ? 1 : 0);
        }
        ListIterator listIterator4 = this.listIterator();
        ListIterator<K> listIterator5 = list.listIterator();
        while (listIterator4.hasNext() && listIterator5.hasNext()) {
            int n = ((Comparable)listIterator4.next()).compareTo(listIterator5.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator5.hasNext() ? -1 : (listIterator4.hasNext() ? 1 : 0);
    }

    @Override
    public void push(K k) {
        this.add(k);
    }

    @Override
    public K pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.remove(this.size() - 1);
    }

    @Override
    public K top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (K)this.get(this.size() - 1);
    }

    @Override
    public K peek(int n) {
        return (K)this.get(this.size() - 1 - n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object e = objectIterator.next();
            if (this == e) {
                stringBuilder.append("(this list)");
                continue;
            }
            stringBuilder.append(String.valueOf(e));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ObjectIterator iterator() {
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
    public static class ObjectSubList<K>
    extends AbstractObjectList<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectList<K> l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();

        public ObjectSubList(ObjectList<K> objectList, int n, int n2) {
            this.l = objectList;
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
        public boolean add(K k) {
            this.l.add(this.to, k);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, K k) {
            this.ensureIndex(n);
            this.l.add(this.from + n, k);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends K> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public K get(int n) {
            this.ensureRestrictedIndex(n);
            return (K)this.l.get(this.from + n);
        }

        @Override
        public K remove(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return (K)this.l.remove(this.from + n);
        }

        @Override
        public K set(int n, K k) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, k);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, Object[] objectArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, objectArray, n2, n3);
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
        public void addElements(int n, K[] KArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, KArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            this.ensureIndex(n);
            return new ObjectListIterator<K>(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
                final int val$index;
                final ObjectSubList this$0;
                {
                    this.this$0 = objectSubList;
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
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.get(this.this$0.from + this.last);
                }

                @Override
                public K previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.get(this.this$0.from + this.pos);
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
                public void add(K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, k);
                    this.last = -1;
                    if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(K k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.remove(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ObjectList<K> subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ObjectSubList<K>(this, n, n2);
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
        public ObjectIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(ObjectSubList objectSubList) {
            return objectSubList.assertRange();
        }
    }
}

