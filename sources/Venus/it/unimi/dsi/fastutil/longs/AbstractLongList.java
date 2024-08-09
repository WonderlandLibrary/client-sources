/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import it.unimi.dsi.fastutil.longs.LongStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractLongList
extends AbstractLongCollection
implements LongList,
LongStack {
    protected AbstractLongList() {
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
    public void add(int n, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(long l) {
        this.add(this.size(), l);
        return false;
    }

    @Override
    public long removeLong(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long set(int n, long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Long> collection) {
        this.ensureIndex(n);
        Iterator<? extends Long> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, (long)iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Long> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public LongListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public LongListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public LongListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new LongListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractLongList this$0;
            {
                this.this$0 = abstractLongList;
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
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(long l) {
                this.this$0.add(this.pos++, l);
                this.last = -1;
            }

            @Override
            public void set(long l) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, l);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeLong(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(long l) {
        return this.indexOf(l) >= 0;
    }

    @Override
    public int indexOf(long l) {
        LongListIterator longListIterator = this.listIterator();
        while (longListIterator.hasNext()) {
            long l2 = longListIterator.nextLong();
            if (l != l2) continue;
            return longListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(long l) {
        LongListIterator longListIterator = this.listIterator(this.size());
        while (longListIterator.hasPrevious()) {
            long l2 = longListIterator.previousLong();
            if (l != l2) continue;
            return longListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(0L);
            }
        } else {
            while (n2-- != n) {
                this.removeLong(n2);
            }
        }
    }

    @Override
    public LongList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new LongSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        LongListIterator longListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            longListIterator.nextLong();
            longListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, long[] lArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > lArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + lArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, lArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, long[] lArray) {
        this.addElements(n, lArray, 0, lArray.length);
    }

    @Override
    public void getElements(int n, long[] lArray, int n2, int n3) {
        LongListIterator longListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > lArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + lArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            lArray[n2++] = longListIterator.nextLong();
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
        LongListIterator longListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            long l = longListIterator.nextLong();
            n = 31 * n + HashCommon.long2int(l);
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
        if (list instanceof LongList) {
            LongListIterator longListIterator = this.listIterator();
            LongListIterator longListIterator2 = ((LongList)list).listIterator();
            while (n-- != 0) {
                if (longListIterator.nextLong() == longListIterator2.nextLong()) continue;
                return true;
            }
            return false;
        }
        LongListIterator longListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(longListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Long> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof LongList) {
            LongListIterator longListIterator = this.listIterator();
            LongListIterator longListIterator2 = ((LongList)list).listIterator();
            while (longListIterator.hasNext() && longListIterator2.hasNext()) {
                long l;
                long l2 = longListIterator.nextLong();
                int n = Long.compare(l2, l = longListIterator2.nextLong());
                if (n == 0) continue;
                return n;
            }
            return longListIterator2.hasNext() ? -1 : (longListIterator.hasNext() ? 1 : 0);
        }
        LongListIterator longListIterator = this.listIterator();
        ListIterator<? extends Long> listIterator2 = list.listIterator();
        while (longListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)longListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (longListIterator.hasNext() ? 1 : 0);
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
        return this.removeLong(this.size() - 1);
    }

    @Override
    public long topLong() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getLong(this.size() - 1);
    }

    @Override
    public long peekLong(int n) {
        return this.getLong(this.size() - 1 - n);
    }

    @Override
    public boolean rem(long l) {
        int n = this.indexOf(l);
        if (n == -1) {
            return true;
        }
        this.removeLong(n);
        return false;
    }

    @Override
    public boolean addAll(int n, LongCollection longCollection) {
        this.ensureIndex(n);
        LongIterator longIterator = longCollection.iterator();
        boolean bl = longIterator.hasNext();
        while (longIterator.hasNext()) {
            this.add(n++, longIterator.nextLong());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, LongList longList) {
        return this.addAll(n, (LongCollection)longList);
    }

    @Override
    public boolean addAll(LongCollection longCollection) {
        return this.addAll(this.size(), longCollection);
    }

    @Override
    public boolean addAll(LongList longList) {
        return this.addAll(this.size(), longList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        LongListIterator longListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            long l = longListIterator.nextLong();
            stringBuilder.append(String.valueOf(l));
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
    public static class LongSubList
    extends AbstractLongList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractLongList.class.desiredAssertionStatus();

        public LongSubList(LongList longList, int n, int n2) {
            this.l = longList;
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
        public boolean add(long l) {
            this.l.add(this.to, l);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, long l) {
            this.ensureIndex(n);
            this.l.add(this.from + n, l);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Long> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public long getLong(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getLong(this.from + n);
        }

        @Override
        public long removeLong(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeLong(this.from + n);
        }

        @Override
        public long set(int n, long l) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, l);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, long[] lArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, lArray, n2, n3);
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
        public void addElements(int n, long[] lArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, lArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public LongListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new LongListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractLongList.class.desiredAssertionStatus();
                final int val$index;
                final LongSubList this$0;
                {
                    this.this$0 = longSubList;
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(long l) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, l);
                    this.last = -1;
                    if (!$assertionsDisabled && !LongSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(long l) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, l);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeLong(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !LongSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public LongList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new LongSubList(this, n, n2);
        }

        @Override
        public boolean rem(long l) {
            int n = this.indexOf(l);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeLong(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, LongCollection longCollection) {
            this.ensureIndex(n);
            return super.addAll(n, longCollection);
        }

        @Override
        public boolean addAll(int n, LongList longList) {
            this.ensureIndex(n);
            return super.addAll(n, longList);
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
        public LongIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(LongSubList longSubList) {
            return longSubList.assertRange();
        }
    }
}

