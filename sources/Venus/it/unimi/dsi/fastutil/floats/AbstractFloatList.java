/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.floats.FloatStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloatList
extends AbstractFloatCollection
implements FloatList,
FloatStack {
    protected AbstractFloatList() {
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
    public void add(int n, float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(float f) {
        this.add(this.size(), f);
        return false;
    }

    @Override
    public float removeFloat(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float set(int n, float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Float> collection) {
        this.ensureIndex(n);
        Iterator<? extends Float> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, iterator2.next().floatValue());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Float> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public FloatListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public FloatListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public FloatListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new FloatListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractFloatList this$0;
            {
                this.this$0 = abstractFloatList;
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
            public float nextFloat() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getFloat(this.last);
            }

            @Override
            public float previousFloat() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getFloat(this.pos);
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
            public void add(float f) {
                this.this$0.add(this.pos++, f);
                this.last = -1;
            }

            @Override
            public void set(float f) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, f);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeFloat(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(float f) {
        return this.indexOf(f) >= 0;
    }

    @Override
    public int indexOf(float f) {
        FloatListIterator floatListIterator = this.listIterator();
        while (floatListIterator.hasNext()) {
            float f2 = floatListIterator.nextFloat();
            if (Float.floatToIntBits(f) != Float.floatToIntBits(f2)) continue;
            return floatListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(float f) {
        FloatListIterator floatListIterator = this.listIterator(this.size());
        while (floatListIterator.hasPrevious()) {
            float f2 = floatListIterator.previousFloat();
            if (Float.floatToIntBits(f) != Float.floatToIntBits(f2)) continue;
            return floatListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add(0.0f);
            }
        } else {
            while (n2-- != n) {
                this.removeFloat(n2);
            }
        }
    }

    @Override
    public FloatList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new FloatSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        FloatListIterator floatListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            floatListIterator.nextFloat();
            floatListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, float[] fArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > fArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + fArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, fArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, float[] fArray) {
        this.addElements(n, fArray, 0, fArray.length);
    }

    @Override
    public void getElements(int n, float[] fArray, int n2, int n3) {
        FloatListIterator floatListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > fArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + fArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            fArray[n2++] = floatListIterator.nextFloat();
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
        FloatListIterator floatListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            float f = floatListIterator.nextFloat();
            n = 31 * n + HashCommon.float2int(f);
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
        if (list instanceof FloatList) {
            FloatListIterator floatListIterator = this.listIterator();
            FloatListIterator floatListIterator2 = ((FloatList)list).listIterator();
            while (n-- != 0) {
                if (floatListIterator.nextFloat() == floatListIterator2.nextFloat()) continue;
                return true;
            }
            return false;
        }
        FloatListIterator floatListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(floatListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Float> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof FloatList) {
            FloatListIterator floatListIterator = this.listIterator();
            FloatListIterator floatListIterator2 = ((FloatList)list).listIterator();
            while (floatListIterator.hasNext() && floatListIterator2.hasNext()) {
                float f;
                float f2 = floatListIterator.nextFloat();
                int n = Float.compare(f2, f = floatListIterator2.nextFloat());
                if (n == 0) continue;
                return n;
            }
            return floatListIterator2.hasNext() ? -1 : (floatListIterator.hasNext() ? 1 : 0);
        }
        FloatListIterator floatListIterator = this.listIterator();
        ListIterator<? extends Float> listIterator2 = list.listIterator();
        while (floatListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)floatListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (floatListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(float f) {
        this.add(f);
    }

    @Override
    public float popFloat() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeFloat(this.size() - 1);
    }

    @Override
    public float topFloat() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getFloat(this.size() - 1);
    }

    @Override
    public float peekFloat(int n) {
        return this.getFloat(this.size() - 1 - n);
    }

    @Override
    public boolean rem(float f) {
        int n = this.indexOf(f);
        if (n == -1) {
            return true;
        }
        this.removeFloat(n);
        return false;
    }

    @Override
    public boolean addAll(int n, FloatCollection floatCollection) {
        this.ensureIndex(n);
        FloatIterator floatIterator = floatCollection.iterator();
        boolean bl = floatIterator.hasNext();
        while (floatIterator.hasNext()) {
            this.add(n++, floatIterator.nextFloat());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, FloatList floatList) {
        return this.addAll(n, (FloatCollection)floatList);
    }

    @Override
    public boolean addAll(FloatCollection floatCollection) {
        return this.addAll(this.size(), floatCollection);
    }

    @Override
    public boolean addAll(FloatList floatList) {
        return this.addAll(this.size(), floatList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        FloatListIterator floatListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            float f = floatListIterator.nextFloat();
            stringBuilder.append(String.valueOf(f));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public FloatIterator iterator() {
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
    public static class FloatSubList
    extends AbstractFloatList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractFloatList.class.desiredAssertionStatus();

        public FloatSubList(FloatList floatList, int n, int n2) {
            this.l = floatList;
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
        public boolean add(float f) {
            this.l.add(this.to, f);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(int n, float f) {
            this.ensureIndex(n);
            this.l.add(this.from + n, f);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Float> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public float getFloat(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getFloat(this.from + n);
        }

        @Override
        public float removeFloat(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeFloat(this.from + n);
        }

        @Override
        public float set(int n, float f) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, f);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, float[] fArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, fArray, n2, n3);
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
        public void addElements(int n, float[] fArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, fArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public FloatListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new FloatListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractFloatList.class.desiredAssertionStatus();
                final int val$index;
                final FloatSubList this$0;
                {
                    this.this$0 = floatSubList;
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
                public float nextFloat() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getFloat(this.this$0.from + this.last);
                }

                @Override
                public float previousFloat() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getFloat(this.this$0.from + this.pos);
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
                public void add(float f) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, f);
                    this.last = -1;
                    if (!$assertionsDisabled && !FloatSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(float f) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, f);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeFloat(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !FloatSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public FloatList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new FloatSubList(this, n, n2);
        }

        @Override
        public boolean rem(float f) {
            int n = this.indexOf(f);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeFloat(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, FloatCollection floatCollection) {
            this.ensureIndex(n);
            return super.addAll(n, floatCollection);
        }

        @Override
        public boolean addAll(int n, FloatList floatList) {
            this.ensureIndex(n);
            return super.addAll(n, floatList);
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
        public FloatIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(FloatSubList floatSubList) {
            return floatSubList.assertRange();
        }
    }
}

