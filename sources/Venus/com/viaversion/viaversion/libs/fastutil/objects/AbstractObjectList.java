/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Stack;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;

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
        return new ObjectIterators.AbstractIndexBasedListIterator<K>(this, 0, n){
            final AbstractObjectList this$0;
            {
                this.this$0 = abstractObjectList;
                super(n, n2);
            }

            @Override
            protected final K get(int n) {
                return this.this$0.get(n);
            }

            @Override
            protected final void add(int n, K k) {
                this.this$0.add(n, k);
            }

            @Override
            protected final void set(int n, K k) {
                this.this$0.set(n, k);
            }

            @Override
            protected final void remove(int n) {
                this.this$0.remove(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$0.size();
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
        return this instanceof RandomAccess ? new ObjectRandomAccessSubList(this, n, n2) : new ObjectSubList(this, n, n2);
    }

    @Override
    public void forEach(Consumer<? super K> consumer) {
        if (this instanceof RandomAccess) {
            int n = this.size();
            for (int i = 0; i < n; ++i) {
                consumer.accept(this.get(i));
            }
        } else {
            ObjectList.super.forEach(consumer);
        }
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
        ObjectArrays.ensureOffsetLength(KArray, n2, n3);
        if (this instanceof RandomAccess) {
            while (n3-- != 0) {
                this.add(n++, KArray[n2++]);
            }
        } else {
            ListIterator listIterator2 = this.listIterator(n);
            while (n3-- != 0) {
                listIterator2.add(KArray[n2++]);
            }
        }
    }

    @Override
    public void addElements(int n, K[] KArray) {
        this.addElements(n, KArray, 0, KArray.length);
    }

    @Override
    public void getElements(int n, Object[] objectArray, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(objectArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            int n4 = n;
            while (n3-- != 0) {
                objectArray[n2++] = this.get(n4++);
            }
        } else {
            ListIterator listIterator2 = this.listIterator(n);
            while (n3-- != 0) {
                objectArray[n2++] = listIterator2.next();
            }
        }
    }

    @Override
    public void setElements(int n, K[] KArray, int n2, int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(KArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        if (this instanceof RandomAccess) {
            for (int i = 0; i < n3; ++i) {
                this.set(i + n, KArray[i + n2]);
            }
        } else {
            ListIterator listIterator2 = this.listIterator(n);
            int n4 = 0;
            while (n4 < n3) {
                listIterator2.next();
                listIterator2.set(KArray[n2 + n4++]);
            }
        }
    }

    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }

    @Override
    public Object[] toArray() {
        int n = this.size();
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        Object[] objectArray = new Object[n];
        this.getElements(0, objectArray, 0, n);
        return objectArray;
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        int n = this.size();
        if (TArray.length < n) {
            TArray = Arrays.copyOf(TArray, n);
        }
        this.getElements(0, TArray, 0, n);
        if (TArray.length > n) {
            TArray[n] = null;
        }
        return TArray;
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
            if (Objects.equals(listIterator2.next(), listIterator3.next())) continue;
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

    public static class ObjectRandomAccessSubList<K>
    extends ObjectSubList<K>
    implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public ObjectRandomAccessSubList(ObjectList<K> objectList, int n, int n2) {
            super(objectList, n, n2);
        }

        @Override
        public ObjectList<K> subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ObjectRandomAccessSubList<K>(this, n, n2);
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }
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
        public void setElements(int n, K[] KArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.setElements(this.from + n, KArray, n2, n3);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            this.ensureIndex(n);
            return this.l instanceof RandomAccess ? new RandomAccessIter(this, n) : new ParentWrappingIter(this, this.l.listIterator(n + this.from));
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator<K>(this.l, this.from, this.to) : super.spliterator();
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
        public Spliterator spliterator() {
            return this.spliterator();
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

        private final class RandomAccessIter
        extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            static final boolean $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
            final ObjectSubList this$0;

            RandomAccessIter(ObjectSubList objectSubList, int n) {
                this.this$0 = objectSubList;
                super(0, n);
            }

            @Override
            protected final K get(int n) {
                return this.this$0.l.get(this.this$0.from + n);
            }

            @Override
            protected final void add(int n, K k) {
                this.this$0.add(n, k);
            }

            @Override
            protected final void set(int n, K k) {
                this.this$0.set(n, k);
            }

            @Override
            protected final void remove(int n) {
                this.this$0.remove(n);
            }

            @Override
            protected final int getMaxPos() {
                return this.this$0.to - this.this$0.from;
            }

            @Override
            public void add(K k) {
                super.add(k);
                if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                    throw new AssertionError();
                }
            }

            @Override
            public void remove() {
                super.remove();
                if (!$assertionsDisabled && !ObjectSubList.access$000(this.this$0)) {
                    throw new AssertionError();
                }
            }
        }

        private class ParentWrappingIter
        implements ObjectListIterator<K> {
            private ObjectListIterator<K> parent;
            final ObjectSubList this$0;

            ParentWrappingIter(ObjectSubList objectSubList, ObjectListIterator<K> objectListIterator) {
                this.this$0 = objectSubList;
                this.parent = objectListIterator;
            }

            @Override
            public int nextIndex() {
                return this.parent.nextIndex() - this.this$0.from;
            }

            @Override
            public int previousIndex() {
                return this.parent.previousIndex() - this.this$0.from;
            }

            @Override
            public boolean hasNext() {
                return this.parent.nextIndex() < this.this$0.to;
            }

            @Override
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= this.this$0.from;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.next();
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previous();
            }

            @Override
            public void add(K k) {
                this.parent.add(k);
            }

            @Override
            public void set(K k) {
                this.parent.set(k);
            }

            @Override
            public void remove() {
                this.parent.remove();
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.parent.previousIndex();
                int n3 = n2 - n;
                if (n3 < this.this$0.from - 1) {
                    n3 = this.this$0.from - 1;
                }
                int n4 = n3 - n2;
                return this.parent.back(n4);
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = this.parent.nextIndex();
                int n3 = n2 + n;
                if (n3 > this.this$0.to) {
                    n3 = this.this$0.to;
                }
                int n4 = n3 - n2;
                return this.parent.skip(n4);
            }
        }
    }

    static final class IndexBasedSpliterator<K>
    extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
        final ObjectList<K> l;

        IndexBasedSpliterator(ObjectList<K> objectList, int n) {
            super(n);
            this.l = objectList;
        }

        IndexBasedSpliterator(ObjectList<K> objectList, int n, int n2) {
            super(n, n2);
            this.l = objectList;
        }

        @Override
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override
        protected final K get(int n) {
            return (K)this.l.get(n);
        }

        @Override
        protected final IndexBasedSpliterator<K> makeForSplit(int n, int n2) {
            return new IndexBasedSpliterator<K>(this.l, n, n2);
        }

        @Override
        protected ObjectSpliterator makeForSplit(int n, int n2) {
            return this.makeForSplit(n, n2);
        }
    }
}

