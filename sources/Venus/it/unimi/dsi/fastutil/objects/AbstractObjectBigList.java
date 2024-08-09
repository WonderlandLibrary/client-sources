/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
import it.unimi.dsi.fastutil.objects.ObjectBigList;
import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractObjectBigList<K>
extends AbstractObjectCollection<K>
implements ObjectBigList<K>,
Stack<K> {
    protected AbstractObjectBigList() {
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
    public void add(long l, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(K k) {
        this.add(this.size64(), k);
        return false;
    }

    @Override
    public K remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public K set(long l, K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends K> collection) {
        this.ensureIndex(l);
        Iterator<K> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends K> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public ObjectBigListIterator<K> iterator() {
        return this.listIterator();
    }

    @Override
    public ObjectBigListIterator<K> listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public ObjectBigListIterator<K> listIterator(long l) {
        this.ensureIndex(l);
        return new ObjectBigListIterator<K>(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractObjectBigList this$0;
            {
                this.this$0 = abstractObjectBigList;
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
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(K k) {
                this.this$0.add(this.pos++, k);
                this.last = -1L;
            }

            @Override
            public void set(K k) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0L;
    }

    @Override
    public long indexOf(Object object) {
        BigListIterator bigListIterator = this.listIterator();
        while (bigListIterator.hasNext()) {
            Object e = bigListIterator.next();
            if (!Objects.equals(object, e)) continue;
            return bigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(Object object) {
        BigListIterator bigListIterator = this.listIterator(this.size64());
        while (bigListIterator.hasPrevious()) {
            Object k = bigListIterator.previous();
            if (!Objects.equals(object, k)) continue;
            return bigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add((K)null);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public ObjectBigList<K> subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new ObjectSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        BigListIterator bigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            bigListIterator.next();
            bigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, K[][] KArray, long l2, long l3) {
        this.ensureIndex(l);
        ObjectBigArrays.ensureOffsetLength(KArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, ObjectBigArrays.get(KArray, l2++));
        }
    }

    @Override
    public void addElements(long l, K[][] KArray) {
        this.addElements(l, KArray, 0L, ObjectBigArrays.length(KArray));
    }

    @Override
    public void getElements(long l, Object[][] objectArray, long l2, long l3) {
        BigListIterator bigListIterator = this.listIterator(l);
        ObjectBigArrays.ensureOffsetLength(objectArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            ObjectBigArrays.set(objectArray, l2++, bigListIterator.next());
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
        ObjectIterator objectIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
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
        if (!(object instanceof BigList)) {
            return true;
        }
        BigList bigList = (BigList)object;
        long l = this.size64();
        if (l != bigList.size64()) {
            return true;
        }
        BigListIterator bigListIterator = this.listIterator();
        BigListIterator bigListIterator2 = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(bigListIterator.next(), bigListIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends K> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof ObjectBigList) {
            BigListIterator bigListIterator = this.listIterator();
            BigListIterator bigListIterator2 = ((ObjectBigList)bigList).listIterator();
            while (bigListIterator.hasNext() && bigListIterator2.hasNext()) {
                Object e;
                Object e2 = bigListIterator.next();
                int n = ((Comparable)e2).compareTo(e = bigListIterator2.next());
                if (n == 0) continue;
                return n;
            }
            return bigListIterator2.hasNext() ? -1 : (bigListIterator.hasNext() ? 1 : 0);
        }
        BigListIterator bigListIterator = this.listIterator();
        BigListIterator<K> bigListIterator3 = bigList.listIterator();
        while (bigListIterator.hasNext() && bigListIterator3.hasNext()) {
            int n = ((Comparable)bigListIterator.next()).compareTo(bigListIterator3.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator3.hasNext() ? -1 : (bigListIterator.hasNext() ? 1 : 0);
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
        return this.remove(this.size64() - 1L);
    }

    @Override
    public K top() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.get(this.size64() - 1L);
    }

    @Override
    public K peek(int n) {
        return this.get(this.size64() - 1L - (long)n);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object e = objectIterator.next();
            if (this == e) {
                stringBuilder.append("(this big list)");
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
    public int compareTo(Object object) {
        return this.compareTo((BigList)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class ObjectSubList<K>
    extends AbstractObjectBigList<K>
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectBigList<K> l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractObjectBigList.class.desiredAssertionStatus();

        public ObjectSubList(ObjectBigList<K> objectBigList, long l, long l2) {
            this.l = objectBigList;
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
        public boolean add(K k) {
            this.l.add(this.to, k);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, K k) {
            this.ensureIndex(l);
            this.l.add(this.from + l, k);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends K> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public K get(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.get(this.from + l);
        }

        @Override
        public K remove(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.remove(this.from + l);
        }

        @Override
        public K set(long l, K k) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, k);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, Object[][] objectArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, objectArray, l2, l3);
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
        public void addElements(long l, K[][] KArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, KArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ObjectBigListIterator<K> listIterator(long l) {
            this.ensureIndex(l);
            return new ObjectBigListIterator<K>(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractObjectBigList.class.desiredAssertionStatus();
                final long val$index;
                final ObjectSubList this$0;
                {
                    this.this$0 = objectSubList;
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
                public long nextIndex() {
                    return this.pos;
                }

                @Override
                public long previousIndex() {
                    return this.pos - 1L;
                }

                @Override
                public void add(K k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, k);
                    this.last = -1L;
                    if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(K k) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.remove(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public ObjectBigList<K> subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new ObjectSubList<K>(this, l, l2);
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
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public ObjectIterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((BigList)object);
        }

        static boolean access$000(ObjectSubList objectSubList) {
            return objectSubList.assertRange();
        }
    }
}

